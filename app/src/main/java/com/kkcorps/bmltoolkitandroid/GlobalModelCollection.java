package com.kkcorps.bmltoolkitandroid;

import com.kkcorps.bmltoolkitandroid.BasicLearningTemplate.BasicLearningItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 21/3/15.
 */
public class GlobalModelCollection {
    public static List<Model> globalCollectionList = new ArrayList<Model>();

    public GlobalModelCollection(Model model){

    }

    public GlobalModelCollection(List<Model> learningItems){
        globalCollectionList.clear();
        globalCollectionList.addAll(learningItems);
    }

    public Model get(int i){
        return globalCollectionList.get(i);
    }

    public Model set(int i, Model item){
        return globalCollectionList.set(i, item);
    }
    public void add(Model item){
        globalCollectionList.add(item);
    }

    public void addAll(List<Model> learningItems){
        globalCollectionList.addAll(learningItems);
    }

    public void update(List<Model> learningItems){
        globalCollectionList.clear();
        globalCollectionList.addAll(learningItems);
    }
    public Model remove(int i){
        return globalCollectionList.remove(i);
    }
}
