;; gorilla-repl.fileformat = 1

;; **
;;; # bayesian-ballot/infer.clj
;; **

;; @@
(use 'nstools.ns)
(ns+ message-example
     (:like anglican-user.worksheet)
     (:require [clojure-csv.core :as csv]
               [clojure.java.io :as io]))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"[nil,nil]"}
;; <=

;; @@
; Utills

(defn index-of [item coll]
  (count (take-while (partial not= item) coll)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;message-example/index-of</span>","value":"#'message-example/index-of"}
;; <=

;; @@
(defn take-csv
  [fname]
  (with-open [file (io/reader fname)]
    (csv/parse-csv (slurp file))))

; Files
(def vote_result_sample (take-csv "bayesian-ballot/data/vote_result_sample.csv"))
(def vote_result_1521 (take-csv "bayesian-ballot/data/vote_result_1521.csv"))

(def vote_result vote_result_sample)

; Key member
(def members (rest (first vote_result)))
(def key-idx (index-of "염동열" members))

; Encoding
(defn encode-row [vr-row]
  (let [key-choice (nth vr-row key-idx)]
    (map (fn [x] (= key-choice x)) vr-row)))

; ((true true true true true false ...) ...)
(def encoded-vote
  (map encode-row (filter
                    (fn [row] (not (= (nth row key-idx) "abs")))
                    (map rest (rest vote_result)))))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/take-csv</span>","value":"#'message-example/take-csv"},{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/vote_result_sample</span>","value":"#'message-example/vote_result_sample"}],"value":"[#'message-example/take-csv,#'message-example/vote_result_sample]"},{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/vote_result_1521</span>","value":"#'message-example/vote_result_1521"}],"value":"[[#'message-example/take-csv,#'message-example/vote_result_sample],#'message-example/vote_result_1521]"},{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/vote_result</span>","value":"#'message-example/vote_result"}],"value":"[[[#'message-example/take-csv,#'message-example/vote_result_sample],#'message-example/vote_result_1521],#'message-example/vote_result]"},{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/members</span>","value":"#'message-example/members"}],"value":"[[[[#'message-example/take-csv,#'message-example/vote_result_sample],#'message-example/vote_result_1521],#'message-example/vote_result],#'message-example/members]"},{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/key-idx</span>","value":"#'message-example/key-idx"}],"value":"[[[[[#'message-example/take-csv,#'message-example/vote_result_sample],#'message-example/vote_result_1521],#'message-example/vote_result],#'message-example/members],#'message-example/key-idx]"},{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/encode-row</span>","value":"#'message-example/encode-row"}],"value":"[[[[[[#'message-example/take-csv,#'message-example/vote_result_sample],#'message-example/vote_result_1521],#'message-example/vote_result],#'message-example/members],#'message-example/key-idx],#'message-example/encode-row]"},{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/encoded-vote</span>","value":"#'message-example/encoded-vote"}],"value":"[[[[[[[#'message-example/take-csv,#'message-example/vote_result_sample],#'message-example/vote_result_1521],#'message-example/vote_result],#'message-example/members],#'message-example/key-idx],#'message-example/encode-row],#'message-example/encoded-vote]"}
;; <=

;; @@

;; @@
