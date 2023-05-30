# Cyclopeptide Sequencing

Cyclopeptide Sequencing is an algorithm for identifying a cyclic peptide from its theoretical spectrum. This repository provides an implementation of the Cyclopeptide Sequencing algorithm in Java.

## Table of Contents
- [Introduction](#introduction)
- [Usage](#usage)
- [Algorithm Overview](#algorithm-overview)
- [Input and Output](#input-and-output)
- [Example](#example)

## Introduction

Cyclopeptide Sequencing is a problem in mass spectrometry-based proteomics, where the goal is to reconstruct a cyclic peptide sequence based on its theoretical spectrum. The theoretical spectrum is a list of masses of all possible subpeptides of the cyclic peptide.

This implementation provides a solution to the Cyclopeptide Sequencing problem using a brute-force approach. It generates all possible candidate peptides and compares their theoretical spectra to the given experimental spectrum to find the correct cyclic peptide sequence.

## Usage

To use the Cyclopeptide Sequencing algorithm, you need to have Java installed on your system. Follow these steps:

1. Clone this repository to your local machine or download the source code files.
2. Compile the Java files using the following command: javac CyclopeptideSequencing.java
3. Run the compiled program using the following command: java CyclopeptideSequencing
4. Follow the prompts on the command line interface to provide the experimental spectrum and view the output.

## Algorithm Overview

The Cyclopeptide Sequencing algorithm follows these steps:

1. Read the experimental spectrum from the user.
2. Generate all possible candidate peptides using the experimental spectrum.
3. Compute the theoretical spectrum for each candidate peptide.
4. Compare the theoretical spectrum of each candidate peptide with the experimental spectrum.
5. Identify the candidate peptide whose theoretical spectrum matches the experimental spectrum.
6. Output the identified cyclic peptide sequence.

## Input and Output

The input to the Cyclopeptide Sequencing algorithm is the experimental spectrum, which is a space-separated list of integer masses representing the peaks observed in the mass spectrometry experiment.

The output of the algorithm is the identified cyclic peptide sequence, which is displayed on the command line interface.

## Example

Here's an example to illustrate the usage and output of the Cyclopeptide Sequencing algorithm: 

Enter the experimental spectrum: 0 113 128 186 241 299 314 427

Identified cyclic peptide sequence: 186-128-113

In this example, the experimental spectrum is provided as input, and the algorithm identifies the cyclic peptide sequence as "186-128-113".








