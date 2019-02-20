package com.yensontam.imagesearch;

import java.util.Arrays;
import java.util.TreeSet;

// optional: make this singleton
public class PrimeNumbers {

    // optional: can load this in a constructor, so takes up less space when not needed
    private static TreeSet<Integer> KNOWN_PRIMES = new TreeSet<Integer>(Arrays.asList(
            2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67,
            71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139,
            149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223,
            227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293,
            307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383,
            389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463,
            467, 479, 487, 491, 499));

    public static boolean isPrime(int n) {

        if (n < 2) return false;
        if (n % 1 != 0) return false;

        if (KNOWN_PRIMES.contains(n)) {
            return true;
        } else {
            double root = Math.sqrt(n);
            if (root <= KNOWN_PRIMES.last()) {
                for (Integer i : KNOWN_PRIMES.subSet(2,true, (int) root,true)) {
                    if (n % i == 0) {
                        return false;
                    }
                }
                return true;
            } else {
                for (int i = 2; i <= Math.floor(root); i++) {
                    if (n % i == 0) {
                        return false;
                    }
                }
                return true;
            }
        }
    }
}
