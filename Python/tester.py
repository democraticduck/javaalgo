from algorithm.sorting_algorithm import MergeSort, MergeSortStep, QuickSort, QuickSortStep
from algorithm.searching_algorithm import BinarySearchStep
from algorithm.data_processing_utils import read_data, read_section_data, save_merge_steps, save_quick_steps, save_sorted_data, read_sorted_data, save_search_steps, binary_search_test, save_search_result

import os, time

class Tester:
    @staticmethod
    def run():
        print("=== Python Algorithm Tester ===")
        filename = input("Enter dataset CSV filename (e.g., path\dataset_sample_1000.csv): ")

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
            intArrayList = list(data.keys())
            mergeSortStep = MergeSortStep()
            result = mergeSortStep.execute(intArrayList, 0, len(intArrayList) - 1)
            save_merge_steps(start, end, result, data)

        elif choice == "2":
            start = int(input("Start row: "))
            end = int(input("End row: "))
            data = read_section_data(filename, start, end)
            intArrayList = list(data.keys())
            quickSortStep = QuickSortStep()
            result = quickSortStep.execute(intArrayList, 0, len(intArrayList) - 1)
            save_quick_steps(start, end, result, data)

        elif choice == "3":
            target = int(input("Enter Target: "))
            data = read_sorted_data(filename)
            intArrayList = [int(x) for x, y in data]
            binarySearchStep = BinarySearchStep()
            result = binarySearchStep.execute(intArrayList, 0, len(intArrayList) - 1, target)
            save_search_steps(result, data, target)

        elif choice == "4":
            data = read_data(filename)
            intArrayList = list(data.keys())
            mergeSort = MergeSort()
            start_time = time.perf_counter()
            result = mergeSort.mergeSort(intArrayList, 0, len(intArrayList) - 1)
            end_time = time.perf_counter()
            print("Merge Sort Time: {:.6f} seconds".format(end_time - start_time) + " or " + "{:.6f} ms".format((end_time - start_time) * 1000))
            save_sorted_data(result, data, len(intArrayList), "m")

        elif choice == "5":
            data = read_data(filename)
            intArrayList = list(data.keys())
            quickSort = QuickSort()
            start_time = time.perf_counter()
            result = quickSort.quickSort(intArrayList, 0, len(intArrayList) - 1)
            end_time = time.perf_counter()
            print("Quick Sort Time: {:.6f} seconds".format(end_time - start_time) + " or " + "{:.6f} ms".format((end_time - start_time) * 1000))
            save_sorted_data(result, data, len(intArrayList), "q")

        elif choice == "6":
            data = read_data(filename)
            intArrayList = list(data.keys())
            result = binary_search_test(intArrayList)
            save_search_result(result, len(intArrayList))

        else:
            print("Invalid choice.")

#----------------------------------------------

def main():
    Tester.run()

if __name__ == "__main__":
    main()
