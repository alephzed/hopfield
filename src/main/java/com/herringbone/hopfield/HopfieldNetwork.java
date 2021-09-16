package com.herringbone.hopfield;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
@Slf4j
public class HopfieldNetwork {

    @Value("${dimension}")
    private Integer dimension;

    private int[][] weightMatrix;

    private final PatternRepository patternRepository;

    public HopfieldNetwork(PatternRepository patternRepository) {
        this.patternRepository = patternRepository;
    }

    @PostConstruct
    private void init() {
        this.weightMatrix = new int[this.dimension][this.dimension];
    }

    public void train(int[] pattern) {
        int[] bipolarPattern = Utils.transform(pattern);

        int[][] patternWeightMatrix;
        patternWeightMatrix = Matrix.outerProduct(bipolarPattern);
        patternWeightMatrix = Matrix.clearDiagonals(patternWeightMatrix);

        this.weightMatrix = Matrix.addMatrix(weightMatrix, patternWeightMatrix);
    }

    public int[] recall(int[] pattern) {
        int[] bipolarPattern = Utils.transform(pattern);

        int[] result = Matrix.matrixVectorMultiplication(weightMatrix, bipolarPattern);

        //apply the sign activation function
//        for(int i=0; i< bipolarPattern.length; ++i) {
//            result[i] = ActivationFunction.activation(result[i]);
//        }
        result = Arrays.stream(result).map(ActivationFunction::activation).toArray();

        int[] recalledPattern =  Utils.retransform(result);
        Integer[] boxed = Arrays.stream(recalledPattern).boxed().toArray(Integer[]::new);
        HopfieldPattern pattern1 = patternRepository.findByMatrix(boxed);
        log.info("Pattern: " + pattern1);
        return recalledPattern;
    }
}
