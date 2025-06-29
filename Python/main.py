from algorithm.sorting_algorithm import MergeSort, MergeSortStep, QuickSort, QuickSortStep
from algorithm.searching_algorithm import BinarySearchStep, binary_search_test
from algorithm.data_processing_utils import read_data, read_section_data, save_merge_steps, save_quick_steps, save_sorted_data, read_sorted_data, save_search_steps, save_search_result

import os, time

class Tester:
    @staticmethod
    def run():
        print("=== Python Algorithm Tester ===")
        filename = input("Enter dataset CSV filename (eg. dataset_1000.csv): ")

        if not os.path.isfile(filename):
            print(f"Error: File '{filename}' does not exist.")
            return

        print("Choose operation:")
        print("1. Merge Sort Step")
        print("2. Quick Sort Step")
        print("3. Binary Search Step")
        print("4. Merge Sort")
        print("5. Quick Sort")
        print("6. Binary Search")

        choice = input("Enter your choice: ")

        if choice == "1":
            start = int(input("Start row: "))
            end = int(input("End row: "))
            data = read_section_data(filename, start, end)
            intArrayList = list(data)
            mergeSortStep = MergeSortStep()
            start_time = time.perf_counter()
            result = mergeSortStep.execute(intArrayList, 0, len(intArrayList) - 1)
            end_time = time.perf_counter()
            print("Merge Sort Step: {:.6f} seconds".format(end_time - start_time) + " or " + "{:.6f} ms".format((end_time - start_time) * 1000))
            save_merge_steps(start, end, result, data)

        elif choice == "2":
            start = int(input("Start row: "))
            end = int(input("End row: "))
            data = read_section_data(filename, start, end)
            intArrayList = list(data)
            quickSortStep = QuickSortStep()
            start_time = time.perf_counter()
            result = quickSortStep.execute(intArrayList, 0, len(intArrayList) - 1)
            end_time = time.perf_counter()
            print("Quick Sort Step: {:.6f} seconds".format(end_time - start_time) + " or " + "{:.6f} ms".format((end_time - start_time) * 1000))
            save_quick_steps(start, end, result, data)

        elif choice == "3":
            target = int(input("Enter Target: "))
            data = read_sorted_data(filename)
            intArrayList = [int(x) for x, y in data]
            binarySearchStep = BinarySearchStep()
            start_time = time.perf_counter()
            result = binarySearchStep.execute(intArrayList, 0, len(intArrayList) - 1, target)
            end_time = time.perf_counter()
            print("Binary Search Step: {:.6f} seconds".format(end_time - start_time) + " or " + "{:.6f} ms".format((end_time - start_time) * 1000))
            save_search_steps(result, data, target)

        elif choice == "4":
            data = read_data(filename)
            intArrayList = list(data)
            mergeSort = MergeSort()
            start_time = time.perf_counter()
            mergeSort.mergeSort(intArrayList, 0, len(intArrayList) - 1)
            end_time = time.perf_counter()
            print("Merge Sort: {:.6f} seconds".format(end_time - start_time) + " or " + "{:.6f} ms".format((end_time - start_time) * 1000))
            save_sorted_data(intArrayList, data, len(intArrayList), "m")

        elif choice == "5":
            data = read_data(filename)
            intArrayList = list(data)
            quickSort = QuickSort()
            start_time = time.perf_counter()
            quickSort.quickSort(intArrayList, 0, len(intArrayList) - 1)
            end_time = time.perf_counter()
            print("Quick Sort: {:.6f} seconds".format(end_time - start_time) + " or " + "{:.6f} ms".format((end_time - start_time) * 1000))
            save_sorted_data(intArrayList, data, len(intArrayList), "q")

        elif choice == "6":
            data = read_data(filename)
            intArrayList = list(data)
            result = binary_search_test(intArrayList)
            print(f"Best Case Time    : {result[0]:.6f} ms")
            print(f"Average Case Time : {result[1]:.6f} ms")
            print(f"Worst Case Time   : {result[2]:.6f} ms")
            save_search_result(result, len(intArrayList))

        else:
            print("Invalid choice.")

#----------------------------------------------

def main():
    Tester.run()

if __name__ == "__main__":
    main()
