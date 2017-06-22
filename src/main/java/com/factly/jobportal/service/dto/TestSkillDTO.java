package com.factly.jobportal.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the TestSkill entity.
 */
public class TestSkillDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String skill;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TestSkillDTO testSkillDTO = (TestSkillDTO) o;
        if(testSkillDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), testSkillDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TestSkillDTO{" +
            "id=" + getId() +
            ", skill='" + getSkill() + "'" +
            "}";
    }
}
