package com.herringbone.hopfield;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@SpringBootApplication
@EnableJpaRepositories
@Slf4j
public class HopfieldApplication {

    public static void main(String[] args) {
        SpringApplication.run(HopfieldApplication.class, args);
    }

    @Component
    class NetworkTrainer implements CommandLineRunner {

        private final HopfieldNetwork hopfieldNetwork;

        private final PatternRepository patternRepository;

        NetworkTrainer(HopfieldNetwork hopfieldNetwork, PatternRepository patternRepository) {
            this.hopfieldNetwork = hopfieldNetwork;
            this.patternRepository = patternRepository;
        }

        @Override
        public void run(String... args) throws Exception {
            int[] letterCMatrix = new int[] {1, 1, 1, 1, 0, 0, 1, 1, 1};
            int[] letterTMatrix = new int[] {1, 1, 1, 0, 1, 0, 0, 1, 0};
            Integer[] letterCMatrixCopy = new Integer[] {1, 1, 1, 1, 0, 0, 1, 1, 1};
            Integer[] letterTMatrixCopy = new Integer[] {1, 1, 1, 0, 1, 0, 0, 1, 0};

            HopfieldPattern pattern = HopfieldPattern.builder()
                    .id(1)
                    .description("The Letter C")
                    .matrix(letterCMatrixCopy)
                    .build();
            HopfieldPattern pattern2 = HopfieldPattern.builder()
                    .id(2)
                    .description("The Letter T")
                    .matrix(letterTMatrixCopy)
                    .build();
            patternRepository.save(pattern);
            patternRepository.save(pattern2);

            // this is the array representation of the letter C
            hopfieldNetwork.train(letterCMatrix);
//            hopfieldNetwork.train(new int[] {1, 1, 1, 1, 0, 0, 1, 1, 1});
            // this is the numerical representation of the letter T
//            hopfieldNetwork.train(new int[] {1, 1, 1, 0, 1, 0, 0, 1, 0});
            hopfieldNetwork.train(letterTMatrix);

            //test with noisy letter C
            hopfieldNetwork.recall(new int[] {1, 1, 0, 1, 0, 0, 1, 1, 1});

            //test with noisy letter T
            hopfieldNetwork.recall(new int[] {1, 0, 1, 0, 1, 0, 0, 1, 0});

            HopfieldPattern pattern1 = patternRepository.findByMatrix(letterCMatrixCopy);
            log.info("Pattern: " + pattern1);
        }
    }

    @RestController
    class HopfieldController {
        //todo create endpoint for recall
        private final HopfieldNetwork hopfieldNetwork;

        HopfieldController(HopfieldNetwork hopfieldNetwork) {
            this.hopfieldNetwork = hopfieldNetwork;
        }

        @PostMapping("/recall")
        int[] recallPattern(@RequestBody ArrayList<Integer> pattern) {
            int[] recallPattern = pattern.stream().mapToInt(i -> i).toArray();
            return hopfieldNetwork.recall(recallPattern);
        }
    }

}
