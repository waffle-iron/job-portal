package com.factly.jobportal.service.util;

import com.factly.jobportal.service.dto.JobListDTO;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.InternalAggregations;
import org.elasticsearch.search.aggregations.bucket.nested.InternalNested;
import org.elasticsearch.search.aggregations.bucket.nested.InternalReverseNested;
import org.elasticsearch.search.aggregations.bucket.nested.NestedBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ntalla on 6/30/17.
 */
public class ESAggregationUtil {

    public static NestedBuilder buildNestedAggresgation(String nestedObjectName, String nestedObjectPropertyName) {
        String outerAggregationName = nestedObjectName + "Agg";
        String innerAggregationName = nestedObjectName + "_" + nestedObjectPropertyName + "Agg";

        NestedBuilder clientTypeAggregation = AggregationBuilders
            .nested(outerAggregationName)
            .path(nestedObjectName)
            .subAggregation(
                AggregationBuilders
                    .terms(innerAggregationName)
                    .field(nestedObjectName+"."+nestedObjectPropertyName)
            );

        return clientTypeAggregation;
    }

    public static TermsBuilder buildAggregation(String nestedObjectName) {
        TermsBuilder organizationAggregation = AggregationBuilders
            .terms(nestedObjectName+"Agg")
            .field(nestedObjectName);

        return organizationAggregation;
    }

    public static Map<String, Long> iterateAggregationResults(String nestedObjectName,
                                                       Map<String, Aggregation> result) {
        String outerAggregationName = nestedObjectName + "Agg";
        Map<String, Long> finalResult = new HashMap<>();

        StringTerms topTags = (StringTerms) result.get(outerAggregationName);
        List<Terms.Bucket> buckets = topTags.getBuckets();
        for (Terms.Bucket entry : buckets) {
            String key = (String) entry.getKey();      // Term
            Long count = entry.getDocCount(); // Doc count

            finalResult.put(key, count);
        }

        return finalResult;
    }

    public static void iterateNestedAggregationResults(JobListDTO jobListDTO,
                                                       Map<String, Aggregation> result) {
        result.forEach((k, v) -> {

            try {
                Map<String, Long> finalResult = new HashMap<>();

                InternalNested in = (InternalNested)result.get(k);
                InternalAggregations aggs = in.getAggregations();

                Map<String, Aggregation> innerAggs = aggs.getAsMap();

                innerAggs.forEach((k1, v1) -> {
                    StringTerms agg = (StringTerms)aggs.getAsMap().get(k1);
                    List<Terms.Bucket> buckets = agg.getBuckets();
                    for (Terms.Bucket entry : buckets) {
                        String key = (String) entry.getKey();      // Term
                        Long count = entry.getDocCount(); // Doc count

                        finalResult.put(key, count);
                    }
                });
                collectAggregationResults(finalResult, jobListDTO, k);
            } catch (ClassCastException cce) {
                Map<String, Long> finalResult = new HashMap<>();

                StringTerms topTags = (StringTerms) result.get(k);
                List<Terms.Bucket> buckets = topTags.getBuckets();
                for (Terms.Bucket entry : buckets) {
                    String key = (String) entry.getKey();      // Term
                    Long count = entry.getDocCount(); // Doc count

                    finalResult.put(key, count);
                }
                collectAggregationResults(finalResult, jobListDTO, k);
            }
        });
    }

    public static void iterateReverseNestedAggregationResults(JobListDTO jobListDTO,
                                                       Map<String, Aggregation> result) {
        result.forEach((k, v) -> {
            Map<String, Long> finalResult = new HashMap<>();

            InternalNested in = (InternalNested)result.get(k);
            InternalAggregations aggs = in.getAggregations();

            Map<String, Aggregation> innerAggs = aggs.getAsMap();
            innerAggs.forEach((k1, v1) -> {
                StringTerms agg = (StringTerms)aggs.getAsMap().get(k1);
                List<Terms.Bucket> buckets = agg.getBuckets();
                for (Terms.Bucket entry : buckets) {
                    String key = (String) entry.getKey();      // Term
                    Long count = entry.getDocCount(); // Doc count

                    InternalReverseNested innerReverseNestedAgg = (InternalReverseNested)entry.getAggregations().asList().get(0);
                    InternalSum internalSum = (InternalSum)innerReverseNestedAgg.getAggregations().asList().get(0);
                    Double sum = internalSum.getValue();
                    finalResult.put(key, sum.longValue());

                }
            });
            collectReverseNestedAggregationResults(finalResult, jobListDTO, k);

        });
    }

    public static List<AbstractAggregationBuilder> createClientTypeAndJobSectorAggregation() {

        String nestedObjectName = "clientType";
        String nestedObjectPropertyName = "type";
        String reverseNestedPropertyName = "totalVacancyCount";

        String outerAggregationName = nestedObjectName + "Agg";
        String outerReverseAggregationName = nestedObjectName + "_" + reverseNestedPropertyName;
        String outerReverseAggregationStatName = reverseNestedPropertyName + "Sum";
        String innerAggregationName = nestedObjectName + "_" + nestedObjectPropertyName + "Agg";


        NestedBuilder clientTypeAggregation = AggregationBuilders
            .nested(outerAggregationName)
            .path(nestedObjectName)
            .subAggregation(
                AggregationBuilders
                    .terms(innerAggregationName)
                    .field(nestedObjectName+"."+nestedObjectPropertyName)
                .subAggregation(
                    AggregationBuilders.reverseNested(outerReverseAggregationName)
                    .subAggregation(AggregationBuilders.sum(outerReverseAggregationStatName).field(reverseNestedPropertyName)))
            );

        nestedObjectName = "jobSector";
        nestedObjectPropertyName = "sector";
        reverseNestedPropertyName = "totalVacancyCount";

        outerAggregationName = nestedObjectName + "Agg";
        outerReverseAggregationName = nestedObjectName + "_" + reverseNestedPropertyName;
        outerReverseAggregationStatName = reverseNestedPropertyName + "Sum";
        innerAggregationName = nestedObjectName + "_" + nestedObjectPropertyName + "Agg";


        NestedBuilder jobSectorAggregation = AggregationBuilders
            .nested(outerAggregationName)
            .path(nestedObjectName)
            .subAggregation(
                AggregationBuilders
                    .terms(innerAggregationName)
                    .field(nestedObjectName+"."+nestedObjectPropertyName)
                    .subAggregation(
                        AggregationBuilders.reverseNested(outerReverseAggregationName)
                            .subAggregation(AggregationBuilders.sum(outerReverseAggregationStatName).field(reverseNestedPropertyName)))
            );

        List<AbstractAggregationBuilder> aggs = new ArrayList<>();
        aggs.add(clientTypeAggregation);
        aggs.add(jobSectorAggregation);
        return aggs;
    }

    public static List<AbstractAggregationBuilder> createAggregations() {
        List<AbstractAggregationBuilder> aggs = new ArrayList<>();
        // add nested aggregations
        NestedBuilder clientTypeAggregation =
            buildNestedAggresgation("clientType","type");
        NestedBuilder jobSectorAggregation =
            buildNestedAggresgation("jobSector","sector");
        NestedBuilder jobTypeAggregation =
            buildNestedAggresgation("jobType","type");
        NestedBuilder educationAggregation =
            buildNestedAggresgation("education","education");

        // add direct aggregations
        TermsBuilder organizationAggregation = buildAggregation("organization");
        TermsBuilder jobRoleAggregation = buildAggregation("jobRole");
        TermsBuilder jobLocationAggregation = buildAggregation("jobLocation");

        aggs.add(clientTypeAggregation);
        aggs.add(jobSectorAggregation);
        aggs.add(jobTypeAggregation);
        aggs.add(educationAggregation);
        aggs.add(organizationAggregation);
        aggs.add(jobRoleAggregation);
        aggs.add(jobLocationAggregation);

        return aggs;
    }

    public static void collectReverseNestedAggregationResults(Map<String, Long> result, JobListDTO jobListDTO, String propertyName) {

        if("clientTypeAgg".equalsIgnoreCase(propertyName)) {
            jobListDTO.setClientTypeReverseNestedAggregations(result);
        } else if("jobSectorAgg".equalsIgnoreCase(propertyName)) {
            jobListDTO.setJobSectorReverseNestedAggregations(result);
        }
    }

    public static void collectAggregationResults(Map<String, Long> result, JobListDTO jobListDTO, String propertyName) {


        if("clientTypeAgg".equalsIgnoreCase(propertyName)) {
            jobListDTO.setClientTypeAggregations(result);
        } else if("jobSectorAgg".equalsIgnoreCase(propertyName)) {
            jobListDTO.setJobSectorAggregations(result);
        } else if("jobTypeAgg".equalsIgnoreCase(propertyName)) {
            jobListDTO.setJobTypeAggregations(result);
        } else if("educationAgg".equalsIgnoreCase(propertyName)) {
            jobListDTO.setEducationAggregations(result);
        } else if("organizationAgg".equalsIgnoreCase(propertyName)) {
            jobListDTO.setOrganizationAggregations(result);
        } else if("jobRoleAgg".equalsIgnoreCase(propertyName)) {
            jobListDTO.setJobRoleAggregations(result);
        } else if("jobLocationAgg".equalsIgnoreCase(propertyName)) {
            jobListDTO.setJobLocationAggregations(result);
        }

    }
}
