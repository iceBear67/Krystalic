package com.github.icebear67.test;

import org.junit.Test;
import org.nutz.json.Json;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TMP {
    @Test
    public void onTest() throws IOException {
        List<String> material;
        material=Json.fromJsonAsList(String.class,new FileReader(new File("ib.json")));
        List<String> finalMaterial = new ArrayList<>();
        material.forEach(a->{
            if(!a.startsWith("/") && !a.startsWith("!p") && !a.isEmpty() && !a.contains("http")){
                Matcher matcher= Pattern.compile("[A-z0-9_+-]").matcher(a);
                if(!matcher.matches()){
                    finalMaterial.add(a);
                }
            }
        });
        File output=new File("ib-s.json");
        FileWriter fw=new FileWriter(output);
        fw.write(Json.toJson(finalMaterial));
        }
    }
