package com.cloud.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> implements Serializable {

    private static final long serialVersionUID = -275582248840137389L;

    private long total;

    private List<T> data;

    private long count;

    public Page(long total, List<T> data) {
        this.total = total;
        this.count = total;
        this.data = data;
    }
}
