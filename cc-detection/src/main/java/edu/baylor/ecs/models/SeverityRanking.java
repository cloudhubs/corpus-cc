package edu.baylor.ecs.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeverityRanking {

    public static final int LOW_THRESHOLD = 1;
    public static final int MEDIUM_THRESHOLD = 10;
    public static final int HIGH_THRESHOLD = 100;

    private List<Clone> lowSeverityClones;
    private List<Clone> mediumSeverityClones;
    private List<Clone> highSeverityClones;

    public SeverityRanking(List<Clone> clones) {
        lowSeverityClones = new ArrayList<>();
        mediumSeverityClones = new ArrayList<>();
        highSeverityClones = new ArrayList<>();

        for(Clone clone : clones){
            double severityScore = computeSeverityScore(clone);
            if(severityScore > HIGH_THRESHOLD){
                this.highSeverityClones.add(clone);
            } else if (severityScore > MEDIUM_THRESHOLD){
                this.mediumSeverityClones.add(clone);
            } else {
                this.lowSeverityClones.add(clone);
            }
        }
    }

    private double computeSeverityScore(Clone clone){
        double severityScore = 1;

        severityScore += 1.0 / CodeCloneType.toInt(clone.getType());
        severityScore += calculateTokenSimilarity(clone) / Math.max(clone.getMethodRepresentationA().getTokens().size(),
                                  clone.getMethodRepresentationB().getTokens().size()) * 100;
        return severityScore;
    }

    private double calculateTokenSimilarity(Clone clone){
        double totalSim = 0;
        for(Map.Entry<String, Integer> entryA : clone.getMethodRepresentationA().getZip().entrySet()){
            for(Map.Entry<String, Integer> entryB : clone.getMethodRepresentationB().getZip().entrySet()){
                if(entryA.getKey().equals(entryB.getKey())){
                    totalSim += Math.min(entryA.getValue(), entryB.getValue());
                }
            }
        }
        return totalSim;
    }
}
