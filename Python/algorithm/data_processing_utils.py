# Save processed results to output file

import csv

def read_data(filename):
    with open(filename, 'r') as file:
        reader = csv.reader(file)
        all_rows = list(reader)
    
    data = {}
    for row in all_rows:
        data[int(row[0])] = row[1]

    return data


def read_section_data(filename, start_row, end_row):
    with open(filename, 'r') as file:
        reader = csv.reader(file)
        all_rows = list(reader)
    sliced = all_rows[start_row - 1:end_row]

    data = {}
    for row in sliced:
        data[int(row[0])] = row[1]

    return data


def save_merge_steps(start, end, result, data):
    filename = f"merge_sort_step_{start}_{end}.txt"
    with open(filename, 'w') as file:
        for step in result:
            file.write("[")
            count = 0
            total = len(step)
            for x in step:
                number = x
                string = data[number]
                file.write(str(number) + "/" + string)
                count += 1
                if count != total:
                    file.write(", ")
            file.write("]\n")


def save_quick_steps(start, end, result, data):
    filename = f"quick_sort_step_{start}_{end}.txt"
    with open(filename, 'w') as file:
        # First line: the original list
        if isinstance(result[0], tuple):
            initial_list = result[0][1]
        else:
            initial_list = result[0]

        file.write("[")
        count = 0
        total = len(initial_list)
        for x in initial_list:
            file.write(str(x) + "/" + data[x])
            count += 1
            if count != total:
                file.write(", ")
        file.write("]\n")

        # Subsequent lines: each step with pi=index
        for step in result:
            if isinstance(step, tuple):
                pi = step[0]
                current_list = step[1]
                file.write("pi=" + str(pi) + " [")
                count = 0
                total = len(current_list)
                for x in current_list:
                    file.write(str(x) + "/" + data[x])
                    count += 1
                    if count != total:
                        file.write(", ")
                file.write("]\n")


def save_sorted_data(result, data, size, type):
    filename = f"quick_sort_{size}.csv" if type == "q" else f"merge_sort__{size}.csv"
    with open(filename, 'w') as file:
        for x in result:
            x = str(x) + "," + data[x] + "\n"
            file.write(x)


def read_sorted_data(filename):
    with open(filename, 'r') as file:
        reader = csv.reader(file)
        all_rows = list(reader)

    return [[int(row[0]), row[1]] for row in all_rows]


def save_search_steps(result, data, target):
    filename = f"binary_search_step_{target}.txt"
    with open(filename, 'w') as file:
        for x in result:
            if x == -1:
                file.write("-1\n")
            else:
                x = str(x + 1) + ": " + str(data[x][0]) + "/" + data[x][1] + "\n"
                file.write(x)


def binary_search_test(arr):
    from algorithm.searching_algorithm import BinarySearch
    import random, time

    bs = BinarySearch()

    n = len(arr) - 1
    # Best Case
    mid = (0 + n) // 2
    target = arr[mid]
    start_time = time.perf_counter()
    bs.binarySearch(arr, 0, n, target)
    end_time = time.perf_counter()
    best_case_time = (end_time - start_time) * 1000

    # Average Case
    total_time = 0
    for _ in range(100):
        target = random.choice(arr)
        start_time = time.perf_counter()
        bs.binarySearch(arr, 0, n, target)
        end_time = time.perf_counter()
        total_time += (end_time - start_time) * 1000
    average_case_time = total_time / 100

    # Worst Case
    target = max(arr) + 10
    start_time = time.perf_counter()
    bs.binarySearch(arr, 0, n, target)
    end_time = time.perf_counter()
    worst_case_time = (end_time - start_time) * 1000

    return [best_case_time, average_case_time, worst_case_time]


def save_search_result(result, size):
    filename = f"binary_search_{size}.txt"
    with open(filename, 'w') as file:
        file.write(f"Best Case Time    : {result[0]:.6f} ms\n")
        file.write(f"Average Case Time : {result[1]:.6f} ms\n")
        file.write(f"Worst Case Time   : {result[2]:.6f} ms\n")
