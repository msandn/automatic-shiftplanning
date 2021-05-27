package edu.ntnu.betweenAlgorithmAndBackend;

import edu.ntnu.bsc020backend.models.GASettings;
import edu.ntnu.bsc020backend.models.ShiftSchedule;
import edu.ntnu.bsc020backend.models.Solution;
import edu.ntnu.bsc020backend.models.SolutionMessage;
import edu.ntnu.sga.Chromosome;
import edu.ntnu.sga.SGA;

import java.util.ArrayList;
import java.util.List;

/**
 * Context class to run algorithm for abstraction
 */
public class Context {
    GASettings settings;

    /**
     * Constructor for the context class
     * @param settings Requires a settings class to choose what methods to use for genetic algorithm
     */
    public Context(GASettings settings){
        this.settings = settings;
    }

    /**
     * Checks a given shift schedule before running the algorithm for any glaring faults.
     * @param shiftSchedule The shift schedule to evaluate.
     * @return A couple of messages if the shift schedule is bad.
     */
    public SolutionMessage evaluateShiftSchedule(ShiftSchedule shiftSchedule){
        int[][] domain = createDomain(shiftSchedule, settings);
        SolutionMessage solutionMessage = new SolutionMessage("Evaluation of the proposed shift schedule");
        for (int i = 0; i < domain.length; i++) {
            if (domain[i].length<=0){
                solutionMessage.addMessage("Shift nr " + i + " has no possible employees");
            }
        }
        Overlap overlap = new Overlap(0);
        overlap.initialize(shiftSchedule);
        int[] temp = overlap.countOverlaps();

        boolean hello = false;
        for (int i = 0; i < temp.length; i++) {
            if(temp[i]>shiftSchedule.getShifts().size()){
                if (!hello){
                    hello = true;
                    solutionMessage.addMessage("These shifts overlap too much, making this shift schedule impossible");
                }
                solutionMessage.addMessage("Shift nr " + i + " has more overlaps than the number of employees");
            }
        }
        return solutionMessage;
    }

    public SolutionMessage evaluateFinishedSchedule(ShiftSchedule shiftSchedule){
        SolutionMessage solutionMessage = new SolutionMessage("Evaluation of the already finished shift schedule");
        int[] arr = Solution.shiftScheduleToArr(shiftSchedule);


        return solutionMessage;
    }

    /**
     * @param shiftSchedule The shift schedule that should be put trough the algorithm
     * @return The proposed solution given by the algorithm
     */
    public Solution getShiftScheduleSolution(ShiftSchedule shiftSchedule){
        int[][] domain = createDomain(shiftSchedule, settings);

        //Setting up restrictions
        List<Restrictions> restrictions = new ArrayList<>();

        int population = 1000;
        int generation = 100        ;
        int maxScore = Integer.MAX_VALUE / population;
        System.out.println("Max score per chromosome: " + maxScore/4);


        //Overlap
        if (settings.isOverlap())
            restrictions.add(new Overlap(maxScore/5));

        //Max working hours per day
        if(settings.isMaxWorkPerDay())
            restrictions.add(new NormalWorkingHours(1, settings.getWorkingMinPerDay(), maxScore/5));

        //Max working hours per week
        if (settings.isMaxWorkPerWeek())
            restrictions.add(new NormalWorkingHours(7, settings.getWorkingMinPerWeek(), maxScore/5));

        //Max working hours per k, custom
        if (settings.isCustomWorkPerK())
            restrictions.add(new NormalWorkingHours(settings.getCustomWorkDays(), settings.getCustomWorkMin(), maxScore/5));

        //Percentage of employment
        if (settings.isPercentageOfEmployments())
            restrictions.add(new PercentageOfEmployment(settings.isEmploymentOverworkAllowed(), maxScore/5));

        SGA geneticAlgorithm = new SGA(domain);
        for (Restrictions restriction: restrictions) {
            restriction.initialize(shiftSchedule);
            geneticAlgorithm.addFitness(restriction);
        }
        geneticAlgorithm.setGenerations(generation);
        geneticAlgorithm.setPopulationSize(population);

        Chromosome rawSolution = geneticAlgorithm.runSGA();

        CheckChromosome checkChromosome = new CheckChromosome();

        return checkChromosome.checkChromosome(rawSolution, restrictions);
    }

    private int[][] createDomain(ShiftSchedule shiftSchedule, GASettings settings){
        Domain dom = Domain.buildDomain();

        //Setting up domain
        if (settings.isSkills()) dom.addDomainRemover(new Skills());
        if (settings.isHolidays()) dom.addDomainRemover(new Holidays());
        if (settings.isPastDate()) dom.addDomainRemover(new PastDate(settings.getToday()));
        return dom.createDomain(shiftSchedule);
    }
}
