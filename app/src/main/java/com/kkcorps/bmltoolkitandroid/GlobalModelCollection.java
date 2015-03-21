package com.kkcorps.bmltoolkitandroid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 21/3/15.
 */
public class GlobalModelCollection {
    public static List<BasicLearningItem> globalCollectionList = new ArrayList<BasicLearningItem>();

    public GlobalModelCollection(){

    }

    public GlobalModelCollection(List<BasicLearningItem> learningItems){
        globalCollectionList.clear();
        globalCollectionList.addAll(learningItems);
    }

    public BasicLearningItem get(int i){
        return globalCollectionList.get(i);
    }

    public BasicLearningItem set(int i, BasicLearningItem item){
        return globalCollectionList.set(i, item);
    }
    public void add(BasicLearningItem item){
        globalCollectionList.add(item);
    }

    public void addAll(List<BasicLearningItem> learningItems){
        globalCollectionList.addAll(learningItems);
    }

    public void update(List<BasicLearningItem> learningItems){
        globalCollectionList.clear();
        globalCollectionList.addAll(learningItems);
    }
    public BasicLearningItem remove(int i){
        return globalCollectionList.remove(i);
    }
}
