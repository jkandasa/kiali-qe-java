package com.redhat.qe.kiali.model.services;

import com.redhat.qe.kiali.KialiUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Jeeva Kandasamy (jkandasa)
 */

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    private String name;
    private String namespace;
    private Health health;

    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
        Service ser = (Service) other;
        if (!KialiUtils.equalsCheck(name, ser.name)) {
            return false;
        }
        if (!KialiUtils.equalsCheck(namespace, ser.namespace)) {
            return false;
        }
        if (!KialiUtils.equalsCheck(health, ser.health)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((namespace == null) ? 0 : namespace.hashCode());
        result = prime * result + ((health == null) ? 0 : health.hashCode());
        return result;
    }
}
