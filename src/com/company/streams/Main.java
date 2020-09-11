package com.company.streams;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        List<String> someBingoNumbers  = Arrays.asList(
                "N40", "N36",
                "B12", "B6",
                "G52", "G34", "G60", "G50",
                "I26", "I17", "I29",
                "O71");

        // Get all 'g' numbers
        someBingoNumbers.stream()
                        .map(s -> s.toUpperCase())
                        .filter(s->s.startsWith("G"))
                        .sorted()
                        .forEach(System.out::println);

        // Return all 'g' numbers as a result
        List<String> sortedGNumbers = someBingoNumbers
                                                    .stream()
                                                    .map(s -> s.toUpperCase())
                                                    .filter(s->s.startsWith("G"))
                                                    .sorted()
                                                    .collect(Collectors.toList());
        System.out.println("Sorted g numbers from stream result:");
        for (String s : sortedGNumbers) System.out.println(s);

        // Also can use 3 params in .collect - (Supplier, Consumer, Accumulator)
        List<String> anotherSortedGNumbers = someBingoNumbers
                .stream()
                .map(s -> s.toUpperCase())
                .filter(s->s.startsWith("G"))
                .sorted()
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);



        // Creating a stream from scratch using .of - list of distinct items from both streams with count
        System.out.println("--------------------------------------------------------");
        Stream<String> ioNumberStream = Stream.of("I26", "I17", "I29", "O71");
        Stream<String> inNumberStream = Stream.of("N40", "N36", "I26", "I29", "O71");
        Stream<String> concatStream = Stream.concat(ioNumberStream, inNumberStream);
        System.out.println(concatStream
                                        .distinct()
                                        .peek(System.out::println)
                                        .count());



        // Example objects
        Employee jill = new Employee("Jill Hart", 33);
        Employee bob = new Employee("Bob McGimmel", 21);
        Employee jack = new Employee("Jack Harrison", 19);
        Employee billy = new Employee("Billy Jones", 41);

        Department hr = new Department("Human Resources");
        hr.addEmployees(jill);
        hr.addEmployees(bob);
        hr.addEmployees(jack);

        Department accounting = new Department("Accounting");
        accounting.addEmployees(billy);

        List<Department> departments = new ArrayList<>();
        departments.add(hr);
        departments.add(accounting);

        // Listing all employees in all departments
        departments.stream()
                    .flatMap(department -> department.getEmployees().stream())
                    .forEach(System.out::println);

        // Listing all employees by age
        System.out.println("Employees by age");
        Map<Integer, List<Employee>> groupByAge = departments.stream()
                                                                    .flatMap(department -> department.getEmployees().stream())
                                                                    .collect(Collectors.groupingBy(employee -> employee.getAge()));

        // Get youngest employee
        System.out.println("Youngest Employee");
        departments.stream()
                .flatMap(department -> department.getEmployees().stream())
                .reduce((e1, e2) -> e1.getAge() < e2.getAge() ? e1 : e2)
                .ifPresent(System.out::println);

    }
}
