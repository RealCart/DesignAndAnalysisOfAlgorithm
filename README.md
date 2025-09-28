Architecture notes (depth & allocations control)

Metrics. Use a shared Metrics object to track:

maxDepth via enter()/exit() at each recursive call.

comparisons, moves (element assignments/swaps), allocations (bytes or logical objects).

QuickSort depth bound. Use random pivoting and always recurse into the smaller partition first to keep worst-case stack at ~2*floor(log2(n)) + O(1) with high probability.

In‑place QuickSort. Avoid List allocations. A Hoare/DNF partition with indices keeps allocations ≈ 0; only stack frames remain. Random pivot = swap a[pivot] to the front.

MergeSort allocations. Classic top‑down creates many subarrays. For realistic GC/cache behavior, prefer single temporary buffer reused during merges; allocations become O(n) total and predictable. If you keep the simple version, count Arrays.copyOfRange allocations.

Select (Median‑of‑Medians). Operates in place. Only extra is the medians array of size ≈ n/5 per level. Track those as allocations += 4L * groups (or 8L if using long).

Closest Pair (2D). The divide‑and‑conquer keeps two sorted views (Px, Py). Arrays are cloned/sliced (O(n) per level) but overall work is O(n log n). Track strip scans as comparisons; recursion depth is ≈ log2 n.

Recurrence analysis (method + Θ‑result)

MergeSort
Recurrence: T(n) = 2T(n/2) + Θ(n) (split + linear merge). By the Master Theorem (case 2), T(n) = Θ(n log n). Depth is Θ(log n); with a single temp buffer, extra space is Θ(n) and cache behavior is good due to mostly sequential merges.

QuickSort (randomized, in‑place)
Expected recurrence: E[T(n)] = E[T(I)] + E[T(n-1-I)] + Θ(n) with I uniform in [0..n-1]. The harmonic analysis yields E[T(n)] = Θ(n log n). Depth is Θ(log n) w.h.p.; a standard bound is Pr[depth > c·log n] ≤ n^{1-c} for c>2. Worst‑case time is Θ(n²) but extremely unlikely with random pivots.

Deterministic Select (Median‑of‑Medians)
Upper bound: T(n) ≤ T(⌈n/5⌉) + T(7n/10 + O(1)) + Θ(n). By Akra–Bazzi, the linear term dominates, giving T(n) = Θ(n) worst‑case. Depth is Θ(log n) when tail‑recursing into the smaller side.

Closest Pair of Points (2D)
Recurrence: T(n) = 2T(n/2) + Θ(n) (linear merge of Py + strip scan). By the Master Theorem, T(n) = Θ(n log n). The strip checks at most a constant (≤ 7–8) neighbors per point due to packing; recursion depth is Θ(log n).
Constant‑factor discussion (what to look for)

QuickSort: branch mispredictions and partition scheme; random pivot vs median‑of‑three; cache‑friendly contiguous scans.

MergeSort: with single temp buffer vs naïve subarray copies (GC pauses, allocator overhead). Expect flatter curves with the buffered version.

Select: linear but with higher constant than randomized select; small‑n cutoff matters.

Closest Pair: the strip constant (≤8) dominates at large n; copying Py vs stable partition impacts cache.

Summary (fill after running)

Theory vs measurements – alignment: e.g., MergeSort time ≈ c·n log n, QuickSort ≈ c'·n log n with lower constant; Select ≈ c''·n.

Mismatches: e.g., naïve MergeSort shows supralinear behavior due to GC; QuickSort depth spikes without smaller‑side recursion.

Depth bound: observed maxDepth for randomized QS ≤ ~2*floor(log2 n)+b.
