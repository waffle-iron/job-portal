package com.factly.jobportal.service.util;

import com.factly.jobportal.service.dto.JobListDTO;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.InternalAggregations;
import org.elasticsearch.search.aggregations.bucket.nested.InternalNested;
import org.elasticsearch.search.aggregations.bucket.nested.NestedBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;

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

    public static Map<String, Long> iterateNestedAggregationResults(String nestedObjectName,
                                                       String nestedObjectPropertyName,
                                                       Map<String, Aggregation> result) {
        String outerAggregationName = nestedObjectName + "Agg";
        String innerAggregationName = nestedObjectName + "_" + nestedObjectPropertyName + "Agg";
        Map<String, Long> finalResult = new HashMap<>();

        InternalNested in = (InternalNested)result.get(outerAggregationName);
        InternalAggregations aggs = in.getAggregations();
        StringTerms agg = (StringTerms)aggs.getAsMap().get(innerAggregationName);
        List<Terms.Bucket> buckets = agg.getBuckets();
        for (Terms.Bucket entry : buckets) {
            String key = (String) entry.getKey();      // Term
            Long count = entry.getDocCount(); // Doc count

            finalResult.put(key, count);
        }

        return finalResult;
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

    public static void collectAggregationResults(Map<String, Aggregation> result, JobListDTO jobListDTO) {


        jobListDTO.setClientTypeAggregations(
            iterateNestedAggregationResults("clientType", "type", result));

        jobListDTO.setJobSectorAggregations(
            iterateNestedAggregationResults("jobSector","sector", result));

        jobListDTO.setJobTypeAggregations(
            iterateNestedAggregationResults("jobType", "type", result));

        jobListDTO.setEducationAggregations(
            iterateNestedAggregationResults("education", "education", result));

        jobListDTO.setOrganizationAggregations(
            iterateAggregationResults("organization", result));

        jobListDTO.setJobRoleAggregations(
            iterateAggregationResults("jobRole", result));

        jobListDTO.setJobLocationAggregations(
            iterateAggregationResults("jobLocation", result));

    }
}
