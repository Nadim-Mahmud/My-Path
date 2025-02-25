package com.wheelchair.mypath.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadim Mahmud
 * @date 2/24/25
 */
public class Utils {

    public static <T> List<T> getSubArray(List<T> arrayList, int start, int end) {

        return new ArrayList<>(arrayList.subList(start, end));
    }
}
