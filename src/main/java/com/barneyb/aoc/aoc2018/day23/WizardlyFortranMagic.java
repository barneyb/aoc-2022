package com.barneyb.aoc.aoc2018.day23;

import com.barneyb.aoc.util.Solver;
import lombok.Value;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class WizardlyFortranMagic {

    long partOne;
    long partTwo;

    private static Pattern RE_BOT = Pattern.compile("pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(\\d+)");

    public static void main(String[] args) {
        Solver.execute(WizardlyFortranMagic.class,
                WizardlyFortranMagic::getPartOne,
                WizardlyFortranMagic::getPartTwo);
    }

    // fortran from https://www.reddit.com/r/adventofcode/comments/a8s17l/comment/ecdcqs1/
// PROGRAM DAY23
//  TYPE NANOBOT
//     INTEGER(8) :: POS(3),R
//  END TYPE NANOBOT
    private static class NANOBOT {
        int[] POS;
        int R;
    }

    public WizardlyFortranMagic(String input) {
//  TYPE(NANOBOT),ALLOCATABLE :: BOTS(:)
        NANOBOT[] BOTS;
//  INTEGER(8) :: I,J,N,IERR,BIGLOC,PART1
        int I, J, N, BIGLOC, PART1;
//  CHARACTER(LEN=50) :: INLINE
//  INTEGER(8),ALLOCATABLE :: DISTS(:),OUTOFRANGE(:)
        int[] DISTS, OUTOFRANGE;
//  LOGICAL,ALLOCATABLE :: OUTGROUP(:)
        boolean[] OUTGROUP;
//
//  !Input read
//  OPEN(1,FILE='input.txt')
//  N=0
//  DO
//     READ(1,*,IOSTAT=IERR)
//     IF(IERR.NE.0)EXIT
//     N=N+1
//  END DO
//  REWIND(1)
//  ALLOCATE(BOTS(N))
//  BIGGEST=0
//  DO I=1,N
//     READ(1,'(A)')INLINE
//     READ(INLINE(SCAN(INLINE,'<')+1:SCAN(INLINE,'>')-1),*)BOTS(I)%POS
//     READ(INLINE(SCAN(INLINE,'=',.TRUE.)+1:LEN_TRIM(INLINE)),*)BOTS(I)%R
//  END DO
        BOTS = input.lines()
                .map(RE_BOT::matcher)
                .filter(Matcher::matches)
                .map(m -> {
                    var nb = new NANOBOT();
                    nb.POS = new int[]{
                            Integer.parseInt(m.group(1)),
                            Integer.parseInt(m.group(2)),
                            Integer.parseInt(m.group(3))
                    };
                    nb.R = Integer.parseInt(m.group(4));
                    return nb;
                })
                .toArray(NANOBOT[]::new);
        N = BOTS.length;
//
//  !Part 1
//  PART1=0
        PART1 = 0;
//  BIGLOC=MAXLOC(BOTS%R,DIM=1)
        BIGLOC = MAXLOC(BOTS, b -> b.R);
//  DO I=1,N
        for (I = 0; I < N; I++) {
//     IF(SUM(ABS(BOTS(I)%POS-BOTS(BIGLOC)%POS)).LE.BOTS(BIGLOC)%R)PART1=PART1+1
            if (SUM(ABS(MINUS(BOTS[I].POS, BOTS[BIGLOC].POS))) <= BOTS[BIGLOC].R) {
                PART1 += 1;
            }
        }
//  END DO
//  WRITE(*,'("Part 1: ",I0)')PART1
        partOne = PART1;
//
//  !Part 2
//  ALLOCATE(OUTGROUP(N),OUTOFRANGE(N))
        OUTOFRANGE = new int[N];
        OUTGROUP = new boolean[N];
//  OUTGROUP=.FALSE.
        Arrays.fill(OUTGROUP, false);
//  DO
        while (true) {
//     OUTOFRANGE=0
            Arrays.fill(OUTOFRANGE, 0);
//     DO J=1,N
            for (J = 0; J < N; J++) {
//        IF(OUTGROUP(J))CYCLE
                if (OUTGROUP[J]) continue;
//        DO I=1,N
                for (I = 0; I < N; I++) {
//           IF(OUTGROUP(I))CYCLE
                    if (OUTGROUP[I]) continue;
//           IF(SUM(ABS(BOTS(I)%POS-BOTS(J)%POS)).GT.BOTS(I)%R+BOTS(J)%R)OUTOFRANGE(I)=OUTOFRANGE(I)+1
                    if (SUM(ABS(MINUS(BOTS[I].POS, BOTS[J].POS))) > BOTS[I].R + BOTS[J].R) {
                        OUTOFRANGE[I] += 1;
                    }
                }
//        END DO
            }
//     END DO
//     IF(ALL(OUTOFRANGE.EQ.0))EXIT
            if (ALL(OUTOFRANGE, n -> n == 0)) {
                break;
            }
//     OUTGROUP=OUTGROUP.OR.(OUTOFRANGE.EQ.MAXVAL(OUTOFRANGE))
            int maxOOR = MAXVAL(OUTOFRANGE);
            for (I = 0; I < N; I++) {
                OUTGROUP[I] = OUTGROUP[I] || OUTOFRANGE[I] == maxOOR;
            }
        }
//  END DO
//  ALLOCATE(DISTS(N))
        DISTS = new int[N];
//  DISTS=0
        Arrays.fill(DISTS, 0);
//  DO I=1,N
        for (I = 0; I < N; I++) {
//     IF(OUTGROUP(I))CYCLE
            if (OUTGROUP[I]) continue;
//     DISTS(I)=SUM(ABS(BOTS(I)%POS))-BOTS(I)%R
            DISTS[I] = SUM(ABS(BOTS[I].POS)) - BOTS[I].R;
        }
//  END DO
//  WRITE(*,'("Part 2: ",I0)')MAXVAL(DISTS)
        partTwo = MAXVAL(DISTS);
    }
//END PROGRAM DAY23

    /*
     * Functions used above, presumably from the FORTRAN standard library?
     */

    private static <T> int MAXLOC(T[] items, Function<T, Integer> extract) {
        int result = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < items.length; i++) {
            int n = extract.apply(items[i]);
            if (n > max) {
                result = i;
                max = n;
            }
        }
        return result;
    }

    private static int MAXVAL(int[] ns) {
        int result = Integer.MIN_VALUE;
        for (int n : ns) {
            result = Math.max(result, n);
        }
        return result;
    }

    private static boolean ALL(int[] ns, Predicate<Integer> test) {
        for (int n : ns) {
            if (!test.test(n)) {
                return false;
            }
        }
        return true;
    }

    private static int[] MINUS(int[] lhs, int[] rhs) {
        int[] result = new int[lhs.length];
        for (int i = 0; i < lhs.length; i++) {
            result[i] = lhs[i] - rhs[i];
        }
        return result;
    }

    private static int[] ABS(int[] ns) {
        int[] result = new int[ns.length];
        for (int i = 0; i < ns.length; i++) {
            result[i] = Math.abs(ns[i]);
        }
        return result;
    }

    private static int SUM(int[] ns) {
        int result = 0;
        for (int n : ns) {
            result += n;
        }
        return result;
    }

}
