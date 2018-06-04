;; gorilla-repl.fileformat = 1

;; **
;;; # Gorilla REPL
;; **

;; @@
(use 'nstools.ns)
(ns+ Final
     (:like anglican-user.worksheet)
     (:require [clojure-csv.core :as csv]
               [clojure.java.io :as io]
               [gorilla-plot.core :as plot]
               [clojure.string :as str]))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"[nil,nil]"}
;; <=

;; @@
(defn index-of [item coll]
  (count (take-while (partial not= item) coll)))

(defn take-csv
  [fname]
  (with-open [file (io/reader fname)]
    (csv/parse-csv (slurp file))))

; Files
(def vote_result_1521 (take-csv "bayesian-ballot/data/vote_result_1521.csv"))
(def vote_result vote_result_1521)

; Key member
(def members (rest (first vote_result)))
(def key-idx1 (index-of "염동열" members)) ; 158
(def key-idx2 (index-of "홍문종" members))

; Encoding - 1 if same with key member, 0 elsewhere.
(defn encode-row1 [vote]
  (let [key-choice (nth vote key-idx1)]
    (map (fn [x] (if (= key-choice x) 1 0)) vote)))

(defn encode-row2 [vote]
  (let [key-choice (nth vote key-idx2)]
    (map (fn [x] (if (= key-choice x) 1 0)) vote)))

; ((true true true true true false ...) ...)
(def encoded-vote1
  (vec
    (map encode-row1 (filter
                      (fn [row] (not (= (nth row key-idx1) "abs")))
                      (map rest (rest vote_result))))))
(def encoded-vote2
  (vec
    (map encode-row2 (filter
                    (fn [row] (not (= (nth row key-idx2) "abs")))
                    (map rest (rest vote_result))))))

;; Encoeded vote result w.r.t. 염동열 for ith member.
(defn vote-ith1 [i]
  (map (fn [row] (nth row i)) encoded-vote1))

;; Encoeded vote result w.r.t. 홍문종 for ith member.
(defn vote-ith2 [i]
  (map (fn [row] (nth row i)) encoded-vote2))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;Final/index-of</span>","value":"#'Final/index-of"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/take-csv</span>","value":"#'Final/take-csv"}],"value":"[#'Final/index-of,#'Final/take-csv]"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/vote_result_1521</span>","value":"#'Final/vote_result_1521"}],"value":"[[#'Final/index-of,#'Final/take-csv],#'Final/vote_result_1521]"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/vote_result</span>","value":"#'Final/vote_result"}],"value":"[[[#'Final/index-of,#'Final/take-csv],#'Final/vote_result_1521],#'Final/vote_result]"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/members</span>","value":"#'Final/members"}],"value":"[[[[#'Final/index-of,#'Final/take-csv],#'Final/vote_result_1521],#'Final/vote_result],#'Final/members]"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/key-idx1</span>","value":"#'Final/key-idx1"}],"value":"[[[[[#'Final/index-of,#'Final/take-csv],#'Final/vote_result_1521],#'Final/vote_result],#'Final/members],#'Final/key-idx1]"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/key-idx2</span>","value":"#'Final/key-idx2"}],"value":"[[[[[[#'Final/index-of,#'Final/take-csv],#'Final/vote_result_1521],#'Final/vote_result],#'Final/members],#'Final/key-idx1],#'Final/key-idx2]"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/encode-row1</span>","value":"#'Final/encode-row1"}],"value":"[[[[[[[#'Final/index-of,#'Final/take-csv],#'Final/vote_result_1521],#'Final/vote_result],#'Final/members],#'Final/key-idx1],#'Final/key-idx2],#'Final/encode-row1]"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/encode-row2</span>","value":"#'Final/encode-row2"}],"value":"[[[[[[[[#'Final/index-of,#'Final/take-csv],#'Final/vote_result_1521],#'Final/vote_result],#'Final/members],#'Final/key-idx1],#'Final/key-idx2],#'Final/encode-row1],#'Final/encode-row2]"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/encoded-vote1</span>","value":"#'Final/encoded-vote1"}],"value":"[[[[[[[[[#'Final/index-of,#'Final/take-csv],#'Final/vote_result_1521],#'Final/vote_result],#'Final/members],#'Final/key-idx1],#'Final/key-idx2],#'Final/encode-row1],#'Final/encode-row2],#'Final/encoded-vote1]"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/encoded-vote2</span>","value":"#'Final/encoded-vote2"}],"value":"[[[[[[[[[[#'Final/index-of,#'Final/take-csv],#'Final/vote_result_1521],#'Final/vote_result],#'Final/members],#'Final/key-idx1],#'Final/key-idx2],#'Final/encode-row1],#'Final/encode-row2],#'Final/encoded-vote1],#'Final/encoded-vote2]"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/vote-ith1</span>","value":"#'Final/vote-ith1"}],"value":"[[[[[[[[[[[#'Final/index-of,#'Final/take-csv],#'Final/vote_result_1521],#'Final/vote_result],#'Final/members],#'Final/key-idx1],#'Final/key-idx2],#'Final/encode-row1],#'Final/encode-row2],#'Final/encoded-vote1],#'Final/encoded-vote2],#'Final/vote-ith1]"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/vote-ith2</span>","value":"#'Final/vote-ith2"}],"value":"[[[[[[[[[[[[#'Final/index-of,#'Final/take-csv],#'Final/vote_result_1521],#'Final/vote_result],#'Final/members],#'Final/key-idx1],#'Final/key-idx2],#'Final/encode-row1],#'Final/encode-row2],#'Final/encoded-vote1],#'Final/encoded-vote2],#'Final/vote-ith1],#'Final/vote-ith2]"}
;; <=

;; @@
(def partylist (take-csv "bayesian-ballot/data/party.csv"))
(def party (rest partylist))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;Final/partylist</span>","value":"#'Final/partylist"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/party</span>","value":"#'Final/party"}],"value":"[#'Final/partylist,#'Final/party]"}
;; <=

;; @@
;; Plot the samples.
(defn sample-plot [samples]
  (plot/histogram samples :bins 50 :normalise :probability))

;; Return the Sample Mean.
(defn sample-mean [samples]
  (/ (reduce + 0.0 samples) (count samples)))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;Final/sample-plot</span>","value":"#'Final/sample-plot"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/sample-mean</span>","value":"#'Final/sample-mean"}],"value":"[#'Final/sample-plot,#'Final/sample-mean]"}
;; <=

;; @@
;; Inference - probability of same result with key member for non-secret vote.
(defquery non-secret [prob]
  (let [a (+ 1 (sample (poisson 1)))
        b (+ 1 (sample (poisson 1)))]
    (observe (beta a b) prob)
    (sample (beta a b))))

;; Sample n times for ith member.
(defn sample-prob [i n key-mem]
  (let [data (if (= key-mem 0) (vote-ith1 i) (vote-ith2 i))
        freq (reduce + 0 data)
        len (count data)
        prob (/ freq len)]
    (map :result (take n (take-nth 10 (drop 1000 (doquery :ipmcmc non-secret [prob])))))))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;Final/non-secret</span>","value":"#'Final/non-secret"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/sample-prob</span>","value":"#'Final/sample-prob"}],"value":"[#'Final/non-secret,#'Final/sample-prob]"}
;; <=

;; @@
(def raw-attendance (slurp "bayesian-ballot/data/0521_attendance_all.txt"))
(def spt-attendance (vec (cons "0" (rest (str/split raw-attendance #"\s+")))))
(def atd-idxs (filter (fn [x] (= (count (nth spt-attendance x)) 1)) (range 0 (count spt-attendance))))

;; Attendance information of 17 votes at the day of the secret vote.
;; first list: overall attendance, rest: each non-secret vote at the day.
(def attendance
  (loop [atd '()
         i 0]
    (if (= (inc i) (count atd-idxs))
      (reverse (cons (subvec spt-attendance (inc (nth atd-idxs i))) atd))
      (recur (cons (subvec spt-attendance (inc (nth atd-idxs i)) (nth atd-idxs (inc i))) atd)
             (inc i)))))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;Final/raw-attendance</span>","value":"#'Final/raw-attendance"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/spt-attendance</span>","value":"#'Final/spt-attendance"}],"value":"[#'Final/raw-attendance,#'Final/spt-attendance]"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/atd-idxs</span>","value":"#'Final/atd-idxs"}],"value":"[[#'Final/raw-attendance,#'Final/spt-attendance],#'Final/atd-idxs]"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/attendance</span>","value":"#'Final/attendance"}],"value":"[[[#'Final/raw-attendance,#'Final/spt-attendance],#'Final/atd-idxs],#'Final/attendance]"}
;; <=

;; @@
;; 1 if (mem-idx)th member attended (atd-idx)th vote
(defn attended? [atd-idx mem-idx]
  (loop [i 0]
    (if (= i (count (nth attendance atd-idx)))
      0
      (if (= (nth (nth attendance atd-idx) i) (nth members mem-idx))
        1
        (recur (inc i))))))

;; Count attendances of vote except the secret vote at the day.
(defn count-atd [mem-idx]
  (loop [i 1
         sum 0]
    (if (= i (count attendance))
      sum
      (recur (inc i) (+ sum (attended? i mem-idx))))))

;; Number of attendances of non-secret votes at the day for all members.
(def points
  (vec
    (loop [i 0
           p '()]
      (if (= i (count members))
        (reverse p)
        (recur (inc i) (cons (count-atd i) p))))))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;Final/attended?</span>","value":"#'Final/attended?"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/count-atd</span>","value":"#'Final/count-atd"}],"value":"[#'Final/attended?,#'Final/count-atd]"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/points</span>","value":"#'Final/points"}],"value":"[[#'Final/attended?,#'Final/count-atd],#'Final/points]"}
;; <=

;; @@
;; Possible indexes of member who attended the secret vote.
(def real-members (vec (filter (fn [x] (= (attended? 0 x) 1)) (range 0 (count members)))))
(sort (frequencies (map count-atd real-members)))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;Final/real-members</span>","value":"#'Final/real-members"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-long'>9</span>","value":"9"}],"value":"[0 9]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>5</span>","value":"5"}],"value":"[1 5]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[2 1]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>5</span>","value":"5"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[5 1]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>6</span>","value":"6"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[6 1]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>11</span>","value":"11"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[11 2]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>12</span>","value":"12"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[12 1]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>13</span>","value":"13"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[13 1]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>14</span>","value":"14"},{"type":"html","content":"<span class='clj-long'>6</span>","value":"6"}],"value":"[14 6]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>15</span>","value":"15"},{"type":"html","content":"<span class='clj-long'>51</span>","value":"51"}],"value":"[15 51]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>16</span>","value":"16"},{"type":"html","content":"<span class='clj-long'>201</span>","value":"201"}],"value":"[16 201]"}],"value":"([0 9] [1 5] [2 1] [5 1] [6 1] [11 2] [12 1] [13 1] [14 6] [15 51] [16 201])"}],"value":"[#'Final/real-members,([0 9] [1 5] [2 1] [5 1] [6 1] [11 2] [12 1] [13 1] [14 6] [15 51] [16 201])]"}
;; <=

;; @@
;; Assumed indexes of member who attended the secret vote. (overall 275 members)
;(def final-members
;  (vec
;    (filter (fn [x] (or
;                      (> 6 (nth points x))
;                      (< 12 (nth points x)))) real-members)))
(def final-members real-members) ; Assumed remaining four member abstained.

(def final-party (map (fn [x] (nth party x)) final-members))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;Final/final-members</span>","value":"#'Final/final-members"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/final-party</span>","value":"#'Final/final-party"}],"value":"[#'Final/final-members,#'Final/final-party]"}
;; <=

;; @@
;; Lists of sample mean of the probability of same result for each non-secret votes, for final 279 embers.

(def probs1
  (vec (map (fn [x] (sample-mean (sample-prob x 1000 0)))
            final-members)))

(def probs2
  (vec (map (fn [x] (sample-mean (sample-prob x 1000 1)))
            final-members)))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;Final/probs1</span>","value":"#'Final/probs1"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/probs2</span>","value":"#'Final/probs2"}],"value":"[#'Final/probs1,#'Final/probs2]"}
;; <=

;; @@
;; Inference of the transition constant 
(defquery inference [probs key-mem]
  (let [a (sample (uniform-continuous 0 1))
        b (sample (uniform-continuous 0 1))
        pre-trans (sample (normal a b))
        trans (if (< pre-trans 0.5) 0.5 (if (> pre-trans 1.5) 1.5 pre-trans)) ; truncated transition constant.
        real-sum (if (= key-mem 0) 172 141)]
    (loop [sum 0
           ps probs]
      (if (empty? ps)
        (do
          (observe (normal sum 2) real-sum)
          trans)
        (let [pre-p-con (* (first ps) trans)
              p-con (if (> pre-p-con 0.99) 0.99 pre-p-con) ; truncation,
              is-con (if (sample (flip p-con)) 1 0)]
      		(recur (+ sum is-con)
                   (rest ps)))))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;Final/inference</span>","value":"#'Final/inference"}
;; <=

;; @@
;; Lists of sampled of probability of same result with key member at each secret vote.

(def trans-samples1
  (vec
    (map :result
       (take 1000 (take-nth 20 (drop 5000 (doquery :ipmcmc inference [probs1 0])))))))

(def trans-samples2
  (vec
    (map :result
       (take 1000 (take-nth 20 (drop 5000 (doquery :ipmcmc inference [probs2 1])))))))

;; List of sample means
(def trans-mean1 (/ (reduce + 0.0 trans-samples1) (count trans-samples1)))
(def trans-mean2 (/ (reduce + 0.0 trans-samples2) (count trans-samples2)))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;Final/trans-samples1</span>","value":"#'Final/trans-samples1"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/trans-samples2</span>","value":"#'Final/trans-samples2"}],"value":"[#'Final/trans-samples1,#'Final/trans-samples2]"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/trans-mean1</span>","value":"#'Final/trans-mean1"}],"value":"[[#'Final/trans-samples1,#'Final/trans-samples2],#'Final/trans-mean1]"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/trans-mean2</span>","value":"#'Final/trans-mean2"}],"value":"[[[#'Final/trans-samples1,#'Final/trans-samples2],#'Final/trans-mean1],#'Final/trans-mean2]"}
;; <=

;; @@
;; Final random experiment to infer who was rebel.
(defquery final-experiment [probs trans]
  (loop [result '()
         ps probs]
    (if (empty? ps)
      result
      (let [pre-p-con (* (first ps) trans)
            p-con (if (> pre-p-con 0.99) 0.99 pre-p-con) ; truncation,
            is-con (if (sample (flip p-con)) 1 0)]
        (recur (cons is-con result)
               (rest ps))))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;Final/final-experiment</span>","value":"#'Final/final-experiment"}
;; <=

;; @@
(def reasonable-results1
  (loop [i 0
         x '()]
    (if (= i 10)
      x
      (let [final-result (first
                           (map :result
                                (take 1 (take-nth
                                          50 (drop 1000 (doquery
                                                          :ipmcmc
                                                          final-experiment
                                                          [probs1  trans-mean1]))))))
            exp-sum (reduce + 0 final-result)]
        (if (and (< exp-sum 176)
                (> exp-sum 168))
          (recur (inc i) (cons final-result x))
          (recur i x))))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;Final/reasonable-results1</span>","value":"#'Final/reasonable-results1"}
;; <=

;; @@
(def totals1 (reduce (fn [x y] (map + x y)) (first reasonable-results1) (rest reasonable-results1)))
(def cons-list1 (filter (fn [x] (> (nth totals1 x) 7)) (range 0 275)))
(def rebels1 (filter (fn [x] (= (second (nth final-party x)) "더불어민주당")) cons-list1))
(count rebels1)
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;Final/totals1</span>","value":"#'Final/totals1"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/cons-list1</span>","value":"#'Final/cons-list1"}],"value":"[#'Final/totals1,#'Final/cons-list1]"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/rebels1</span>","value":"#'Final/rebels1"}],"value":"[[#'Final/totals1,#'Final/cons-list1],#'Final/rebels1]"},{"type":"html","content":"<span class='clj-unkown'>24</span>","value":"24"}],"value":"[[[#'Final/totals1,#'Final/cons-list1],#'Final/rebels1],24]"}
;; <=

;; @@
(map (fn [x] (first (nth final-party x))) rebels1)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;기동민&quot;</span>","value":"\"기동민\""},{"type":"html","content":"<span class='clj-string'>&quot;김두관&quot;</span>","value":"\"김두관\""},{"type":"html","content":"<span class='clj-string'>&quot;김민기&quot;</span>","value":"\"김민기\""},{"type":"html","content":"<span class='clj-string'>&quot;김병기&quot;</span>","value":"\"김병기\""},{"type":"html","content":"<span class='clj-string'>&quot;김병욱&quot;</span>","value":"\"김병욱\""},{"type":"html","content":"<span class='clj-string'>&quot;김부겸&quot;</span>","value":"\"김부겸\""},{"type":"html","content":"<span class='clj-string'>&quot;김성수&quot;</span>","value":"\"김성수\""},{"type":"html","content":"<span class='clj-string'>&quot;김영진&quot;</span>","value":"\"김영진\""},{"type":"html","content":"<span class='clj-string'>&quot;김종민&quot;</span>","value":"\"김종민\""},{"type":"html","content":"<span class='clj-string'>&quot;김진표&quot;</span>","value":"\"김진표\""},{"type":"html","content":"<span class='clj-string'>&quot;김태년&quot;</span>","value":"\"김태년\""},{"type":"html","content":"<span class='clj-string'>&quot;민병두&quot;</span>","value":"\"민병두\""},{"type":"html","content":"<span class='clj-string'>&quot;박경미&quot;</span>","value":"\"박경미\""},{"type":"html","content":"<span class='clj-string'>&quot;박병석&quot;</span>","value":"\"박병석\""},{"type":"html","content":"<span class='clj-string'>&quot;박찬대&quot;</span>","value":"\"박찬대\""},{"type":"html","content":"<span class='clj-string'>&quot;박홍근&quot;</span>","value":"\"박홍근\""},{"type":"html","content":"<span class='clj-string'>&quot;손혜원&quot;</span>","value":"\"손혜원\""},{"type":"html","content":"<span class='clj-string'>&quot;송기헌&quot;</span>","value":"\"송기헌\""},{"type":"html","content":"<span class='clj-string'>&quot;오영훈&quot;</span>","value":"\"오영훈\""},{"type":"html","content":"<span class='clj-string'>&quot;윤호중&quot;</span>","value":"\"윤호중\""},{"type":"html","content":"<span class='clj-string'>&quot;임종성&quot;</span>","value":"\"임종성\""},{"type":"html","content":"<span class='clj-string'>&quot;전해철&quot;</span>","value":"\"전해철\""},{"type":"html","content":"<span class='clj-string'>&quot;전현희&quot;</span>","value":"\"전현희\""},{"type":"html","content":"<span class='clj-string'>&quot;최인호&quot;</span>","value":"\"최인호\""}],"value":"(\"기동민\" \"김두관\" \"김민기\" \"김병기\" \"김병욱\" \"김부겸\" \"김성수\" \"김영진\" \"김종민\" \"김진표\" \"김태년\" \"민병두\" \"박경미\" \"박병석\" \"박찬대\" \"박홍근\" \"손혜원\" \"송기헌\" \"오영훈\" \"윤호중\" \"임종성\" \"전해철\" \"전현희\" \"최인호\")"}
;; <=

;; @@
(def reasonable-results2
  (loop [i 0
         x '()]
    (if (= i 10)
      x
      (let [final-result (first
                           (map :result
                                (take 1 (take-nth
                                          50 (drop 1000 (doquery
                                                          :ipmcmc
                                                          final-experiment
                                                          [probs2  trans-mean2]))))))
            exp-sum (reduce + 0 final-result)]
        (if (and (< exp-sum 145)
                (> exp-sum 137))
          (recur (inc i) (cons final-result x))
          (recur i x))))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;Final/reasonable-results2</span>","value":"#'Final/reasonable-results2"}
;; <=

;; @@
(def totals2 (reduce (fn [x y] (map + x y)) (first reasonable-results2) (rest reasonable-results2)))
(def cons-list2 (filter (fn [x] (> (nth totals2 x) 7)) (range 0 275)))
(def rebels2 (filter (fn [x] (= (second (nth final-party x)) "더불어민주당")) cons-list2))
(count rebels2)
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;Final/totals2</span>","value":"#'Final/totals2"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/cons-list2</span>","value":"#'Final/cons-list2"}],"value":"[#'Final/totals2,#'Final/cons-list2]"},{"type":"html","content":"<span class='clj-var'>#&#x27;Final/rebels2</span>","value":"#'Final/rebels2"}],"value":"[[#'Final/totals2,#'Final/cons-list2],#'Final/rebels2]"},{"type":"html","content":"<span class='clj-unkown'>6</span>","value":"6"}],"value":"[[[#'Final/totals2,#'Final/cons-list2],#'Final/rebels2],6]"}
;; <=

;; @@
(map (fn [x] (first (nth final-party x))) rebels2)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;김두관&quot;</span>","value":"\"김두관\""},{"type":"html","content":"<span class='clj-string'>&quot;김영호&quot;</span>","value":"\"김영호\""},{"type":"html","content":"<span class='clj-string'>&quot;민병두&quot;</span>","value":"\"민병두\""},{"type":"html","content":"<span class='clj-string'>&quot;송영길&quot;</span>","value":"\"송영길\""},{"type":"html","content":"<span class='clj-string'>&quot;이상민&quot;</span>","value":"\"이상민\""},{"type":"html","content":"<span class='clj-string'>&quot;홍익표&quot;</span>","value":"\"홍익표\""}],"value":"(\"김두관\" \"김영호\" \"민병두\" \"송영길\" \"이상민\" \"홍익표\")"}
;; <=

;; @@

;; @@
