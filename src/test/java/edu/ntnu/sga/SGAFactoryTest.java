package edu.ntnu.sga;

import edu.ntnu.betweenAlgorithmAndBackend.DomainTest;
import edu.ntnu.bsc020backend.models.ShiftSchedule;
import edu.ntnu.bsc020backend.models.ShiftScheduleTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SGAFactoryTest {
    ShiftSchedule shiftSchedule;
    SGAFactory sgaFactory;

    @BeforeEach
    void setUp() {
        shiftSchedule = ShiftScheduleTest.getMockShiftSchedule(1, 1);
        sgaFactory = new SGAFactory(DomainTest.getTestDomain(shiftSchedule));
    }

    @Test
    void newRandomChromosome() {
        Chromosome chromosome = sgaFactory.newRandomChromosome();
        assert chromosome != null;
    }

    @Test
    void newRandomPopulation() {
        Population population = sgaFactory.newRandomPopulation(100);
        assert population.size() == 100;
    }

    @Test
    void newChromosome() {
        Chromosome chromosome = sgaFactory.newChromosome(new int[]{1,2,3,4,5,6,7});
        assertArrayEquals(chromosome.genes, new int[]{1,2,3,4,5,6,7});
    }

    @Test
    void copyChromosome() {
        Chromosome chromosome = sgaFactory.newRandomChromosome();
        Chromosome copy = sgaFactory.copyChromosome(chromosome);
        assertArrayEquals(chromosome.genes, copy.genes);
        assert chromosome.genes != copy.genes;
    }
}