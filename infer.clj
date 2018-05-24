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

(def vote_result vote_result_1521)

; Key member
(def members (rest (first vote_result)))
(defn i [name] (index-of name members))
(def key-idx (i "홍문종"))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/take-csv</span>","value":"#'message-example/take-csv"},{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/vote_result_sample</span>","value":"#'message-example/vote_result_sample"}],"value":"[#'message-example/take-csv,#'message-example/vote_result_sample]"},{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/vote_result_1521</span>","value":"#'message-example/vote_result_1521"}],"value":"[[#'message-example/take-csv,#'message-example/vote_result_sample],#'message-example/vote_result_1521]"},{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/vote_result</span>","value":"#'message-example/vote_result"}],"value":"[[[#'message-example/take-csv,#'message-example/vote_result_sample],#'message-example/vote_result_1521],#'message-example/vote_result]"},{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/members</span>","value":"#'message-example/members"}],"value":"[[[[#'message-example/take-csv,#'message-example/vote_result_sample],#'message-example/vote_result_1521],#'message-example/vote_result],#'message-example/members]"},{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/i</span>","value":"#'message-example/i"}],"value":"[[[[[#'message-example/take-csv,#'message-example/vote_result_sample],#'message-example/vote_result_1521],#'message-example/vote_result],#'message-example/members],#'message-example/i]"},{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/key-idx</span>","value":"#'message-example/key-idx"}],"value":"[[[[[[#'message-example/take-csv,#'message-example/vote_result_sample],#'message-example/vote_result_1521],#'message-example/vote_result],#'message-example/members],#'message-example/i],#'message-example/key-idx]"}
;; <=

;; @@
; Encoding
(defn encode-row [vr-row]
  (let [key-choice (nth vr-row key-idx)]
    (map (fn [x] (if (= key-choice x) 0.9 0.1)) vr-row)))


(defn encoded-vote-idx [other] 
  (map encode-row (filter
                    (fn [row] (and (not (= (nth row key-idx) "abs"))
                                   (not (= (nth row other) "abs"))))
                    (map rest (rest vote_result)))))

; ((true true true true true false ...) ...)
(def encoded-vote 
  (encoded-vote-idx 0))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/encode-row</span>","value":"#'message-example/encode-row"},{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/encoded-vote-idx</span>","value":"#'message-example/encoded-vote-idx"}],"value":"[#'message-example/encode-row,#'message-example/encoded-vote-idx]"},{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/encoded-vote</span>","value":"#'message-example/encoded-vote"}],"value":"[[#'message-example/encode-row,#'message-example/encoded-vote-idx],#'message-example/encoded-vote]"}
;; <=

;; @@
(with-primitive-procedures
  [encoded-vote-idx]
  (defquery one-member-query [idx]
    (let [a (sample (uniform-continuous 1 5))
          b (sample (uniform-continuous 1 5))
          evi (encoded-vote-idx idx)]

      (map (fn [x] (observe (beta a b) x))
           (map (fn [row] (nth row idx))
                evi))

      (sample (beta a b)))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;message-example/one-member-query</span>","value":"#'message-example/one-member-query"}
;; <=

;; @@
(defn samples [query idx]
  (doquery :lmh query [idx]))


(defn sampling [query step take-n drop-n idx]
  (map :result (take-nth step (take take-n (drop drop-n (samples query idx))))))


(def result (sampling one-member-query 10 5000 1000 (i "오세정")))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/samples</span>","value":"#'message-example/samples"},{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/sampling</span>","value":"#'message-example/sampling"}],"value":"[#'message-example/samples,#'message-example/sampling]"},{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/result</span>","value":"#'message-example/result"}],"value":"[[#'message-example/samples,#'message-example/sampling],#'message-example/result]"}
;; <=

;; @@
(plot/histogram result :normalize :probability :bins 50)
;; @@
;; =>
;;; {"type":"vega","content":{"width":400,"height":247.2187957763672,"padding":{"top":10,"left":55,"bottom":40,"right":10},"data":[{"name":"a421d13e-3f9f-451f-9355-2ccc669f3685","values":[{"x":0.027626260130289994,"y":0},{"x":0.047051236063894164,"y":0.002},{"x":0.06647621199749834,"y":0.008},{"x":0.08590118793110252,"y":0.002},{"x":0.1053261638647067,"y":0.006},{"x":0.12475113979831087,"y":0.002},{"x":0.14417611573191505,"y":0.004},{"x":0.16360109166551923,"y":0.002},{"x":0.1830260675991234,"y":0.012},{"x":0.20245104353272758,"y":0.012},{"x":0.22187601946633176,"y":0.01},{"x":0.24130099539993594,"y":0.01},{"x":0.2607259713335401,"y":0.0},{"x":0.28015094726714423,"y":0.006},{"x":0.2995759232007484,"y":0.012},{"x":0.31900089913435253,"y":0.016},{"x":0.3384258750679567,"y":0.02},{"x":0.35785085100156083,"y":0.01},{"x":0.377275826935165,"y":0.01},{"x":0.39670080286876913,"y":0.01},{"x":0.4161257788023733,"y":0.01},{"x":0.43555075473597743,"y":0.01},{"x":0.4549757306695816,"y":0.018},{"x":0.47440070660318573,"y":0.012},{"x":0.4938256825367899,"y":0.026},{"x":0.5132506584703941,"y":0.016},{"x":0.5326756344039982,"y":0.018},{"x":0.5521006103376024,"y":0.03},{"x":0.5715255862712065,"y":0.012},{"x":0.5909505622048107,"y":0.018},{"x":0.6103755381384148,"y":0.016},{"x":0.629800514072019,"y":0.03},{"x":0.6492254900056231,"y":0.044},{"x":0.6686504659392273,"y":0.014},{"x":0.6880754418728314,"y":0.036},{"x":0.7075004178064356,"y":0.028},{"x":0.7269253937400397,"y":0.03},{"x":0.7463503696736439,"y":0.032},{"x":0.765775345607248,"y":0.022},{"x":0.7852003215408522,"y":0.038},{"x":0.8046252974744563,"y":0.052},{"x":0.8240502734080605,"y":0.032},{"x":0.8434752493416646,"y":0.036},{"x":0.8629002252752688,"y":0.028},{"x":0.8823252012088729,"y":0.024},{"x":0.9017501771424771,"y":0.048},{"x":0.9211751530760812,"y":0.038},{"x":0.9406001290096854,"y":0.038},{"x":0.9600251049432895,"y":0.036},{"x":0.9794500808768937,"y":0.018},{"x":0.9988750568104978,"y":0.034},{"x":1.018300032744102,"y":0.002},{"x":1.0377250086777063,"y":0}]}],"marks":[{"type":"line","from":{"data":"a421d13e-3f9f-451f-9355-2ccc669f3685"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"interpolate":{"value":"step-before"},"fill":{"value":"steelblue"},"fillOpacity":{"value":0.4},"stroke":{"value":"steelblue"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}}],"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"a421d13e-3f9f-451f-9355-2ccc669f3685","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":{"data":"a421d13e-3f9f-451f-9355-2ccc669f3685","field":"data.y"}}],"axes":[{"type":"x","scale":"x"},{"type":"y","scale":"y"}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :data [{:name \"a421d13e-3f9f-451f-9355-2ccc669f3685\", :values ({:x 0.027626260130289994, :y 0} {:x 0.047051236063894164, :y 0.002} {:x 0.06647621199749834, :y 0.008} {:x 0.08590118793110252, :y 0.002} {:x 0.1053261638647067, :y 0.006} {:x 0.12475113979831087, :y 0.002} {:x 0.14417611573191505, :y 0.004} {:x 0.16360109166551923, :y 0.002} {:x 0.1830260675991234, :y 0.012} {:x 0.20245104353272758, :y 0.012} {:x 0.22187601946633176, :y 0.01} {:x 0.24130099539993594, :y 0.01} {:x 0.2607259713335401, :y 0.0} {:x 0.28015094726714423, :y 0.006} {:x 0.2995759232007484, :y 0.012} {:x 0.31900089913435253, :y 0.016} {:x 0.3384258750679567, :y 0.02} {:x 0.35785085100156083, :y 0.01} {:x 0.377275826935165, :y 0.01} {:x 0.39670080286876913, :y 0.01} {:x 0.4161257788023733, :y 0.01} {:x 0.43555075473597743, :y 0.01} {:x 0.4549757306695816, :y 0.018} {:x 0.47440070660318573, :y 0.012} {:x 0.4938256825367899, :y 0.026} {:x 0.5132506584703941, :y 0.016} {:x 0.5326756344039982, :y 0.018} {:x 0.5521006103376024, :y 0.03} {:x 0.5715255862712065, :y 0.012} {:x 0.5909505622048107, :y 0.018} {:x 0.6103755381384148, :y 0.016} {:x 0.629800514072019, :y 0.03} {:x 0.6492254900056231, :y 0.044} {:x 0.6686504659392273, :y 0.014} {:x 0.6880754418728314, :y 0.036} {:x 0.7075004178064356, :y 0.028} {:x 0.7269253937400397, :y 0.03} {:x 0.7463503696736439, :y 0.032} {:x 0.765775345607248, :y 0.022} {:x 0.7852003215408522, :y 0.038} {:x 0.8046252974744563, :y 0.052} {:x 0.8240502734080605, :y 0.032} {:x 0.8434752493416646, :y 0.036} {:x 0.8629002252752688, :y 0.028} {:x 0.8823252012088729, :y 0.024} {:x 0.9017501771424771, :y 0.048} {:x 0.9211751530760812, :y 0.038} {:x 0.9406001290096854, :y 0.038} {:x 0.9600251049432895, :y 0.036} {:x 0.9794500808768937, :y 0.018} {:x 0.9988750568104978, :y 0.034} {:x 1.018300032744102, :y 0.002} {:x 1.0377250086777063, :y 0})}], :marks [{:type \"line\", :from {:data \"a421d13e-3f9f-451f-9355-2ccc669f3685\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :interpolate {:value \"step-before\"}, :fill {:value \"steelblue\"}, :fillOpacity {:value 0.4}, :stroke {:value \"steelblue\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"a421d13e-3f9f-451f-9355-2ccc669f3685\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"a421d13e-3f9f-451f-9355-2ccc669f3685\", :field \"data.y\"}}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}]}}"}
;; <=

;; @@

;; @@
