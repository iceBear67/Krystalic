package com.github.icebear67.craftpp.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "blockmachine")
public class DBlockMachine {
    @DatabaseField(unique = true, id = true)
    public int location;
    @DatabaseField
    public String machine;
}
