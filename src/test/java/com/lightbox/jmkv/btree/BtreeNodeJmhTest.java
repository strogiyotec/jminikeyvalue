package com.lightbox.jmkv.btree;

import com.lightbox.jmkv.ArraySort;
import org.junit.Ignore;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Test performance of two sort algorithms in BtreeNode keys.
 * Default sort should be faster but if you need custom logic
 * use parametrized addKey method
 */
@Ignore
@SuppressWarnings({"MagicNumber", "DesignForExtension"})
public class BtreeNodeJmhTest {

    /**
     * Run two sort benchmarks.
     *
     * @throws RunnerException if failed
     */
    @Test
    public void testSortPerformance() throws RunnerException {
        final Options opt = new OptionsBuilder()
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.MILLISECONDS)
                .warmupTime(TimeValue.seconds(1))
                .warmupIterations(5)
                .measurementIterations(2)
                .threads(1)
                .forks(1)
                .shouldFailOnError(true)
                .shouldDoGC(false)
                .build();
        new Runner(opt).run();
    }

    /**
     * Benchmark test for default sort of BtreeNode.
     *
     * @param data Data to use for sort
     */
    @Benchmark
    public void testDefaultSort(final TestData data) {
        final BtreeNode node = new BtreeNode(100, 1);
        data.array.forEach(value -> node.addKey(new NodeKey(value, "")));
    }

    /**
     * Benchmark test for custom sort of BtreeNode.
     *
     * @param data Data to use for sort
     */
    @Benchmark
    public void testCustomSort(final TestData data) {
        final BtreeNode node = new BtreeNode(100, 1);
        data.array
                .forEach(value -> {
                            node.addKey(
                                    new NodeKey(value, ""),
                                    TestData.SORT
                            );
                        }
                );
    }

    /**
     * Class that provides test data for JMH benchmarking.
     */
    @State(Scope.Thread)
    public static class TestData {
        /**
         * Default sort.
         */
        private static final ArraySort SORT = new ArraySort.DefaultSort();

        /**
         * Array of random ints.
         */
        private List<Integer> array;

        /**
         * Setup random ints array.
         */
        @Setup
        public void setup() {
            this.array = new Random()
                    .ints(99)
                    .boxed()
                    .collect(Collectors.toList());
        }
    }
}
