package com.github.icebear67.craftpp.config;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Item {
    public String displayName;
    public List<String> lore = new ArrayList<>();
}
