package com.xxm.syncbase.application.filesync.entity;

import java.util.ArrayList;

public class GenericEntityValues {
    ArrayList<String> stringArrayList;

    public GenericEntityValues() {
    }

    public ArrayList<String> getStringArrayList() {
        return this.stringArrayList;
    }

    public void setStringArrayList(final ArrayList<String> stringArrayList) {
        this.stringArrayList = stringArrayList;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof GenericEntityValues)) {
            return false;
        } else {
            GenericEntityValues other = (GenericEntityValues)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$stringArrayList = this.getStringArrayList();
                Object other$stringArrayList = other.getStringArrayList();
                if (this$stringArrayList == null) {
                    if (other$stringArrayList != null) {
                        return false;
                    }
                } else if (!this$stringArrayList.equals(other$stringArrayList)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof GenericEntityValues;
    }

    public int hashCode() {
        int result = 1;
        Object $stringArrayList = this.getStringArrayList();
        result = result * 59 + ($stringArrayList == null ? 43 : $stringArrayList.hashCode());
        return result;
    }

    public String toString() {
        return "GenericEntityValues(stringArrayList=" + this.getStringArrayList() + ")";
    }
}
