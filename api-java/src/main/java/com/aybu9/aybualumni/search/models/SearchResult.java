package com.aybu9.aybualumni.search.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult {

    private String name;
    private String photoUrl;
    private String type;
    private String sector;
    private String url;

}
