# huffman-encoding-using-d-ary-heaps
The goal of this project is to implement a system that uses Huffman coding so that when enormous amount of data needs to be transferred, the overall data size is reduced. This is done in three phases: Huffman Coding, Encoder and Decoder.

In the first phase, I developed a program to generate Huffman codes using 4-way cache optimized heap. It takes a frequency table (generated from input file) as input, and outputs a code table. This was done after a preliminary analysis of the run time of 3 priority queue structures for performance: Binary Heap, 4-way cache optimized heap, and Pairing Heap. In my analysis (given on page 7), 4-way cache optimized heap yielded the best performance on the sample input data file – sample_input_large.txt, and is therefore used in the program to perform priority queue operations.

In the second phase, I built an encoder that reads an input file (to be compressed), and generates two output files – the compressed version of the input file and the code table. This was done by first constructing the frequency table from the input file and storing it into a HashMap data structure in Java. Then, I invoked the program from phase one and outputted the code table. Once the code table is built, it was used to encode the original input file by replacing each input value by its code. The complete encoded message is outputted in binary format.

In the third phase, I wrote a decoder program that reads two input files – encoded message and code table, and yields the decoded file as output. The decoded message is generated from the encoded message using a decode tree. The algorithm I used for constructing this decode tree from the code table is described on page 8.

