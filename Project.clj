;; gorilla-repl.fileformat = 1

;; **
;;; # Gorilla REPL
;;; 
;; **

;; @@
(use 'nstools.ns)
(ns+ PROJECT
     (:like anglican-user.worksheet)
     (:require [clojure-csv.core :as csv]
               [clojure.java.io :as io]
               [gorilla-plot.core :as plot]))
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
(def key-idx (index-of "염동열" members)) ; 158

; Encoding - 1 if same with key member, 0 elsewhere.
(defn encode-row [vote]
  (let [key-choice (nth vote key-idx)]
    (map (fn [x] (if (= key-choice x) 1 0)) vote)))

; ((true true true true true false ...) ...)
(def encoded-vote
  (map encode-row (filter
                    (fn [row] (not (= (nth row key-idx) "abs")))
                    (map rest (rest vote_result)))))

(defn vote-ith [i]
  (map (fn [row] (nth row i)) encoded-vote))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/index-of</span>","value":"#'PROJECT/index-of"},{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/take-csv</span>","value":"#'PROJECT/take-csv"}],"value":"[#'PROJECT/index-of,#'PROJECT/take-csv]"},{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/vote_result_1521</span>","value":"#'PROJECT/vote_result_1521"}],"value":"[[#'PROJECT/index-of,#'PROJECT/take-csv],#'PROJECT/vote_result_1521]"},{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/vote_result</span>","value":"#'PROJECT/vote_result"}],"value":"[[[#'PROJECT/index-of,#'PROJECT/take-csv],#'PROJECT/vote_result_1521],#'PROJECT/vote_result]"},{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/members</span>","value":"#'PROJECT/members"}],"value":"[[[[#'PROJECT/index-of,#'PROJECT/take-csv],#'PROJECT/vote_result_1521],#'PROJECT/vote_result],#'PROJECT/members]"},{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/key-idx</span>","value":"#'PROJECT/key-idx"}],"value":"[[[[[#'PROJECT/index-of,#'PROJECT/take-csv],#'PROJECT/vote_result_1521],#'PROJECT/vote_result],#'PROJECT/members],#'PROJECT/key-idx]"},{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/encode-row</span>","value":"#'PROJECT/encode-row"}],"value":"[[[[[[#'PROJECT/index-of,#'PROJECT/take-csv],#'PROJECT/vote_result_1521],#'PROJECT/vote_result],#'PROJECT/members],#'PROJECT/key-idx],#'PROJECT/encode-row]"},{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/encoded-vote</span>","value":"#'PROJECT/encoded-vote"}],"value":"[[[[[[[#'PROJECT/index-of,#'PROJECT/take-csv],#'PROJECT/vote_result_1521],#'PROJECT/vote_result],#'PROJECT/members],#'PROJECT/key-idx],#'PROJECT/encode-row],#'PROJECT/encoded-vote]"},{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/vote-ith</span>","value":"#'PROJECT/vote-ith"}],"value":"[[[[[[[[#'PROJECT/index-of,#'PROJECT/take-csv],#'PROJECT/vote_result_1521],#'PROJECT/vote_result],#'PROJECT/members],#'PROJECT/key-idx],#'PROJECT/encode-row],#'PROJECT/encoded-vote],#'PROJECT/vote-ith]"}
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
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/sample-plot</span>","value":"#'PROJECT/sample-plot"},{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/sample-mean</span>","value":"#'PROJECT/sample-mean"}],"value":"[#'PROJECT/sample-plot,#'PROJECT/sample-mean]"}
;; <=

;; @@
(defquery ind-bat-opt [prob]
  (let [a (+ 1 (sample (poisson 1)))
        b (+ 1 (sample (poisson 1)))]
    (observe (beta a b) prob)
    (sample (beta a b))))

(defn sample-prob [i n]
  (let [data (vote-ith i)
        freq (reduce + 0 data)
        len (count data)
        prob (/ freq len)]
    (map :result (take n (take-nth 10 (drop 1000 (doquery :ipmcmc ind-bat-opt [prob])))))))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/ind-bat-opt</span>","value":"#'PROJECT/ind-bat-opt"},{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/sample-prob</span>","value":"#'PROJECT/sample-prob"}],"value":"[#'PROJECT/ind-bat-opt,#'PROJECT/sample-prob]"}
;; <=

;; @@
(defn encode-vec [vote]
  (map (fn [x] (if (= x "pro") 1 (if (= x "con") -1 0))) vote))

(def encoded-vote-vec
  (map encode-vec (map rest (rest vote_result))))

(defn vote-vec-ith [i]
  (map (fn [row] (nth row i)) encoded-vote-vec))

(defn dot-prod [v1 v2]
  (let [l1 (count v1)
        l2 (count v2)]
    (if (not (= l1 l2))
      (println "Fail: Two vectors are in different dimension!")
      (reduce + 0.0 (map (fn [x y] (* x y)) v1 v2)))))

(defn norm [v]
  (Math/sqrt (reduce + 0.0 (map (fn [x] (* x x)) v))))

(defn cos-sim [v1 v2]
  (/ (dot-prod v1 v2) (* (norm v1) (norm v2))))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/encode-vec</span>","value":"#'PROJECT/encode-vec"},{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/encoded-vote-vec</span>","value":"#'PROJECT/encoded-vote-vec"}],"value":"[#'PROJECT/encode-vec,#'PROJECT/encoded-vote-vec]"},{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/vote-vec-ith</span>","value":"#'PROJECT/vote-vec-ith"}],"value":"[[#'PROJECT/encode-vec,#'PROJECT/encoded-vote-vec],#'PROJECT/vote-vec-ith]"},{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/dot-prod</span>","value":"#'PROJECT/dot-prod"}],"value":"[[[#'PROJECT/encode-vec,#'PROJECT/encoded-vote-vec],#'PROJECT/vote-vec-ith],#'PROJECT/dot-prod]"},{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/norm</span>","value":"#'PROJECT/norm"}],"value":"[[[[#'PROJECT/encode-vec,#'PROJECT/encoded-vote-vec],#'PROJECT/vote-vec-ith],#'PROJECT/dot-prod],#'PROJECT/norm]"},{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/cos-sim</span>","value":"#'PROJECT/cos-sim"}],"value":"[[[[[#'PROJECT/encode-vec,#'PROJECT/encoded-vote-vec],#'PROJECT/vote-vec-ith],#'PROJECT/dot-prod],#'PROJECT/norm],#'PROJECT/cos-sim]"}
;; <=

;; @@
;; Comparision with cosine similarity for extreme members.

(def extremes
  (filter
    (fn [z] (not (= (first z) 158)))
    (filter (fn [y] (or (> (second y) 0.9) (< (second y) 0.3)))
        	(map (fn [x] (list x (cos-sim (vote-ith x) (vote-ith key-idx))))
            	 (range 0 (count members))))))

(def extreme-idxs (map first extremes))



(let [sorted-means (sort 
                     (fn [x y] (> (second x) (second y)))
                      (map (fn [x] (list x (sample-mean (sample-prob x 500))))
                           extreme-idxs))
      sorted-sims (sort
                    (fn [x y] (> (second x) (second y)))
                    (map (fn [x] (list x (cos-sim (vote-vec-ith x) (vote-vec-ith key-idx))))
                         extreme-idxs))]
  (dotimes [i (count extreme-idxs)]
    (print (nth sorted-means i))
    (print "\t\t")
    (println (nth sorted-sims i))))
;; @@
;; ->
;;; (215 0.6789729300350764)		(215 0.9476224178571635)
;;; (216 0.6643891121249941)		(77 0.943166255250398)
;;; (117 0.6604070052450335)		(18 0.9426026229428682)
;;; (78 0.6530418623920091)		(216 0.9397062536779157)
;;; (18 0.6524577589400046)		(89 0.9353616138008972)
;;; (84 0.6508349470974351)		(53 0.9326479299254824)
;;; (89 0.6500556025530633)		(229 0.9308598692061039)
;;; (53 0.6499007438592834)		(137 0.9302544037746299)
;;; (264 0.6483274453833451)		(93 0.9301355878078431)
;;; (167 0.6483046141027553)		(26 0.9225145404802342)
;;; (108 0.6469390212750339)		(193 0.9218688342256539)
;;; (137 0.6460349475484202)		(108 0.9190023584860599)
;;; (189 0.6436219067996509)		(78 0.9182522903453137)
;;; (170 0.6418474923684329)		(183 0.9180248694395624)
;;; (205 0.6415610456139949)		(170 0.917941681685472)
;;; (208 0.6415213754957513)		(288 0.9178281875947512)
;;; (77 0.6415194778749813)		(154 0.9177235613162567)
;;; (26 0.6399825781229052)		(90 0.9155713849244773)
;;; (270 0.6399379305045543)		(153 0.9153739934904312)
;;; (93 0.6383571559272265)		(39 0.9136308025905069)
;;; (153 0.6377473067468887)		(117 0.9133097208424896)
;;; (39 0.6372491065105668)		(264 0.9132483936310408)
;;; (123 0.6353931450345165)		(2 0.9129362569256898)
;;; (72 0.6353915240918216)		(292 0.911562033635608)
;;; (55 0.6345317385614996)		(191 0.9103654806964284)
;;; (90 0.6319505651379815)		(160 0.9101196886456783)
;;; (193 0.6296951059994677)		(72 0.9087934457144252)
;;; (229 0.6283043944034918)		(119 0.9085156702491807)
;;; (238 0.6281889458949119)		(212 0.9084298284565174)
;;; (288 0.6280319315394782)		(27 0.9083239884778433)
;;; (283 0.6280093409067442)		(32 0.9074743464332456)
;;; (27 0.6276598808102934)		(55 0.9058184966021336)
;;; (12 0.6268733257670073)		(84 0.9055103640935591)
;;; (183 0.6268167245944792)		(283 0.9046712478226432)
;;; (160 0.6267743484902945)		(208 0.9039075789314285)
;;; (119 0.6260551015221409)		(238 0.903714848243688)
;;; (94 0.6258948554964794)		(270 0.9032081862985791)
;;; (251 0.6255252799804301)		(273 0.9002242622854477)
;;; (143 0.6247249557943456)		(262 0.8984866531270533)
;;; (191 0.6246781112698382)		(189 0.8969764758705898)
;;; (33 0.6238756251885426)		(59 0.8956738732918212)
;;; (32 0.6231603831785796)		(192 0.8946277636430371)
;;; (254 0.6230328635363014)		(167 0.894434995931549)
;;; (79 0.62259526591966)		(135 0.8944224358828835)
;;; (292 0.6221034055037702)		(148 0.8943224330448485)
;;; (154 0.6207294396537546)		(143 0.8938938629613644)
;;; (233 0.6206817686615289)		(280 0.8931002993584557)
;;; (95 0.620178308218398)		(205 0.891560965887681)
;;; (2 0.62017381385162)		(95 0.8905210800378426)
;;; (161 0.6196225706524585)		(123 0.8904223314128802)
;;; (59 0.6191143639953969)		(94 0.8901916843986698)
;;; (280 0.6180284549396776)		(111 0.8893448691488787)
;;; (130 0.6175506459479967)		(33 0.8883035163198099)
;;; (282 0.6172797828088082)		(101 0.8838857890126225)
;;; (135 0.6172416014134992)		(251 0.8819296023414379)
;;; (262 0.6157451270708166)		(44 0.8808596561388202)
;;; (212 0.6149812140804171)		(25 0.8808079590997172)
;;; (174 0.6148288374769482)		(169 0.8807186651939558)
;;; (92 0.6142496038754155)		(16 0.8803601271879445)
;;; (169 0.6135366870192169)		(233 0.8793260029596813)
;;; (101 0.61261655548555)		(175 0.8789433374481371)
;;; (192 0.6116961369251571)		(159 0.8787684996164707)
;;; (50 0.6102074666424273)		(282 0.8783359564798986)
;;; (244 0.6092360537345821)		(174 0.8774721851213702)
;;; (273 0.6076333029053673)		(259 0.8769935516323839)
;;; (14 0.6074749297757751)		(235 0.8768051312189312)
;;; (44 0.6050146490293488)		(130 0.8728781980794996)
;;; (111 0.6040731209118787)		(254 0.8728781980794996)
;;; (16 0.6026990756339423)		(127 0.8718217278232628)
;;; (235 0.6012217713843441)		(79 0.8711763739175747)
;;; (259 0.6008010884077061)		(250 0.8705234252150994)
;;; (175 0.6005320927677034)		(35 0.8692843262325979)
;;; (250 0.6005215467299476)		(92 0.8689353851782265)
;;; (230 0.5992372632967335)		(161 0.8687356137173669)
;;; (148 0.5977605081442229)		(102 0.867800905880665)
;;; (159 0.5967687015935662)		(12 0.8613930523320451)
;;; (25 0.5967363699377204)		(50 0.8611255142969676)
;;; (102 0.5907661259002853)		(14 0.8604189794671139)
;;; (35 0.5857035718896175)		(230 0.8591103566877661)
;;; (127 0.5813162500079946)		(244 0.8572404909118481)
;;; (65 0.3589415789549441)		(126 0.24240245071003988)
;;; (126 0.3502985437918166)		(65 0.2313859292355894)
;;; 
;; <-
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/extremes</span>","value":"#'CS492_PROJECT/extremes"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/extreme-idxs</span>","value":"#'CS492_PROJECT/extreme-idxs"}],"value":"[#'CS492_PROJECT/extremes,#'CS492_PROJECT/extreme-idxs]"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"[[#'CS492_PROJECT/extremes,#'CS492_PROJECT/extreme-idxs],nil]"}
;; <=

;; @@
(def probs
<<<<<<< HEAD
  (vec (map (fn [x] (sample-mean (sample-prob x 1000)))
=======
<<<<<<< HEAD
  (vec (map (fn [x] (sample-mean (sample-prob x 1000)))
=======
  (vec (map (fn [x] (sample-mean (sample-prob x 100)))
>>>>>>> 63a59c8f566d41f08d41423a3a72a55eee8f663c
>>>>>>> 5fabb5d2320c0e3cfe2b92108c05cde5fe6aaf08
            (range 0 (count members)))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/probs</span>","value":"#'PROJECT/probs"}
;; <=

;; @@
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
(empty? '())
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>true</span>","value":"true"}
;; <=

;; @@
>>>>>>> 63a59c8f566d41f08d41423a3a72a55eee8f663c
>>>>>>> 5fabb5d2320c0e3cfe2b92108c05cde5fe6aaf08
(defquery inference [probs]
  (let [a (+ 1 (sample (poisson 1))) ; parameter for trans.
        b (+ 1 (sample (poisson 1))) ; parameter for trans.
        trans (sample (beta a b))]
    (loop [sum 0
           ps probs]
      (if (empty? ps)
        (do
<<<<<<< HEAD
          (observe (normal sum 2) 172) ; 141 for 홍문종, 172 for 염동열
=======
<<<<<<< HEAD
          (observe (normal sum 2) 172) ; 141 for 홍문종, 172 for 염동열
=======
          (observe (normal sum 4) 141)
>>>>>>> 63a59c8f566d41f08d41423a3a72a55eee8f663c
>>>>>>> 5fabb5d2320c0e3cfe2b92108c05cde5fe6aaf08
          trans)
        (let [p-con (* (first ps) trans)
              is-con (if (sample (flip p-con)) 1 0)]
      		(recur (+ sum is-con)
                   (rest ps)))))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/inference</span>","value":"#'PROJECT/inference"}
;; <=

;; @@
(def trans-samples
  (vec
    (map :result
<<<<<<< HEAD
       (take 1000 (take-nth 20 (drop 1000 (doquery :ipmcmc inference [probs])))))))
=======
<<<<<<< HEAD
       (take 1000 (take-nth 20 (drop 1000 (doquery :ipmcmc inference [probs])))))))
=======
       (take 100 (take-nth 10 (drop 1000 (doquery :ipmcmc inference [probs])))))))
>>>>>>> 63a59c8f566d41f08d41423a3a72a55eee8f663c
>>>>>>> 5fabb5d2320c0e3cfe2b92108c05cde5fe6aaf08
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/trans-samples</span>","value":"#'PROJECT/trans-samples"}
;; <=

;; @@
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
trans-samples
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>0.6087566360696021</span>","value":"0.6087566360696021"},{"type":"html","content":"<span class='clj-double'>0.6768629394473786</span>","value":"0.6768629394473786"},{"type":"html","content":"<span class='clj-double'>0.7584904460310258</span>","value":"0.7584904460310258"},{"type":"html","content":"<span class='clj-double'>0.9931969199616333</span>","value":"0.9931969199616333"},{"type":"html","content":"<span class='clj-double'>0.9330257340508935</span>","value":"0.9330257340508935"},{"type":"html","content":"<span class='clj-double'>0.9662778914818718</span>","value":"0.9662778914818718"},{"type":"html","content":"<span class='clj-double'>0.645261837312593</span>","value":"0.645261837312593"},{"type":"html","content":"<span class='clj-double'>0.6396682784891242</span>","value":"0.6396682784891242"},{"type":"html","content":"<span class='clj-double'>0.8804740960553241</span>","value":"0.8804740960553241"},{"type":"html","content":"<span class='clj-double'>0.9847043146860303</span>","value":"0.9847043146860303"},{"type":"html","content":"<span class='clj-double'>0.986246099797052</span>","value":"0.986246099797052"},{"type":"html","content":"<span class='clj-double'>0.9258578352891842</span>","value":"0.9258578352891842"},{"type":"html","content":"<span class='clj-double'>0.24865712052416133</span>","value":"0.24865712052416133"},{"type":"html","content":"<span class='clj-double'>0.11273295381672688</span>","value":"0.11273295381672688"},{"type":"html","content":"<span class='clj-double'>0.49529992378304655</span>","value":"0.49529992378304655"},{"type":"html","content":"<span class='clj-double'>0.4530238699283764</span>","value":"0.4530238699283764"},{"type":"html","content":"<span class='clj-double'>0.987115354349859</span>","value":"0.987115354349859"},{"type":"html","content":"<span class='clj-double'>0.9931969199616333</span>","value":"0.9931969199616333"},{"type":"html","content":"<span class='clj-double'>0.9258578352891842</span>","value":"0.9258578352891842"},{"type":"html","content":"<span class='clj-double'>0.73434798984122</span>","value":"0.73434798984122"},{"type":"html","content":"<span class='clj-double'>0.7862729314132327</span>","value":"0.7862729314132327"},{"type":"html","content":"<span class='clj-double'>0.6890085796827388</span>","value":"0.6890085796827388"},{"type":"html","content":"<span class='clj-double'>0.9764404127267813</span>","value":"0.9764404127267813"},{"type":"html","content":"<span class='clj-double'>0.952975945927778</span>","value":"0.952975945927778"},{"type":"html","content":"<span class='clj-double'>0.874354657077925</span>","value":"0.874354657077925"},{"type":"html","content":"<span class='clj-double'>0.6921608565052283</span>","value":"0.6921608565052283"},{"type":"html","content":"<span class='clj-double'>0.5717502197634339</span>","value":"0.5717502197634339"},{"type":"html","content":"<span class='clj-double'>0.8653550485295183</span>","value":"0.8653550485295183"},{"type":"html","content":"<span class='clj-double'>0.888823978116453</span>","value":"0.888823978116453"},{"type":"html","content":"<span class='clj-double'>0.9527264291997151</span>","value":"0.9527264291997151"},{"type":"html","content":"<span class='clj-double'>0.952975945927778</span>","value":"0.952975945927778"},{"type":"html","content":"<span class='clj-double'>0.9336988696984793</span>","value":"0.9336988696984793"},{"type":"html","content":"<span class='clj-double'>0.7253472974932383</span>","value":"0.7253472974932383"},{"type":"html","content":"<span class='clj-double'>0.6373237313421208</span>","value":"0.6373237313421208"},{"type":"html","content":"<span class='clj-double'>0.8418257774776928</span>","value":"0.8418257774776928"},{"type":"html","content":"<span class='clj-double'>0.998843457243667</span>","value":"0.998843457243667"},{"type":"html","content":"<span class='clj-double'>0.9736678074034607</span>","value":"0.9736678074034607"},{"type":"html","content":"<span class='clj-double'>0.9928872832621787</span>","value":"0.9928872832621787"},{"type":"html","content":"<span class='clj-double'>0.7631225122699464</span>","value":"0.7631225122699464"},{"type":"html","content":"<span class='clj-double'>0.8810807526847219</span>","value":"0.8810807526847219"},{"type":"html","content":"<span class='clj-double'>0.1436010690358218</span>","value":"0.1436010690358218"},{"type":"html","content":"<span class='clj-double'>0.9786540653923581</span>","value":"0.9786540653923581"},{"type":"html","content":"<span class='clj-double'>0.986246099797052</span>","value":"0.986246099797052"},{"type":"html","content":"<span class='clj-double'>0.9330257340508935</span>","value":"0.9330257340508935"},{"type":"html","content":"<span class='clj-double'>0.5027605496853293</span>","value":"0.5027605496853293"},{"type":"html","content":"<span class='clj-double'>0.04920075071576188</span>","value":"0.04920075071576188"},{"type":"html","content":"<span class='clj-double'>0.9991163543599261</span>","value":"0.9991163543599261"},{"type":"html","content":"<span class='clj-double'>0.9149976677073679</span>","value":"0.9149976677073679"},{"type":"html","content":"<span class='clj-double'>0.987115354349859</span>","value":"0.987115354349859"},{"type":"html","content":"<span class='clj-double'>0.9847043146860303</span>","value":"0.9847043146860303"},{"type":"html","content":"<span class='clj-double'>0.9258578352891842</span>","value":"0.9258578352891842"},{"type":"html","content":"<span class='clj-double'>0.7088609366404914</span>","value":"0.7088609366404914"},{"type":"html","content":"<span class='clj-double'>0.8698327235962412</span>","value":"0.8698327235962412"},{"type":"html","content":"<span class='clj-double'>0.44027636454205354</span>","value":"0.44027636454205354"},{"type":"html","content":"<span class='clj-double'>0.9991163543599261</span>","value":"0.9991163543599261"},{"type":"html","content":"<span class='clj-double'>0.9764404127267813</span>","value":"0.9764404127267813"},{"type":"html","content":"<span class='clj-double'>0.9736678074034607</span>","value":"0.9736678074034607"},{"type":"html","content":"<span class='clj-double'>0.9714885968528271</span>","value":"0.9714885968528271"},{"type":"html","content":"<span class='clj-double'>0.8308051734438379</span>","value":"0.8308051734438379"},{"type":"html","content":"<span class='clj-double'>0.34555716547882465</span>","value":"0.34555716547882465"},{"type":"html","content":"<span class='clj-double'>0.9754528545231328</span>","value":"0.9754528545231328"},{"type":"html","content":"<span class='clj-double'>0.998843457243667</span>","value":"0.998843457243667"},{"type":"html","content":"<span class='clj-double'>0.9527264291997151</span>","value":"0.9527264291997151"},{"type":"html","content":"<span class='clj-double'>0.9258578352891842</span>","value":"0.9258578352891842"},{"type":"html","content":"<span class='clj-double'>0.7497312447706685</span>","value":"0.7497312447706685"},{"type":"html","content":"<span class='clj-double'>0.5637059685254562</span>","value":"0.5637059685254562"},{"type":"html","content":"<span class='clj-double'>0.2863142580705646</span>","value":"0.2863142580705646"},{"type":"html","content":"<span class='clj-double'>0.9991163543599261</span>","value":"0.9991163543599261"},{"type":"html","content":"<span class='clj-double'>0.9764404127267813</span>","value":"0.9764404127267813"},{"type":"html","content":"<span class='clj-double'>0.9330257340508935</span>","value":"0.9330257340508935"},{"type":"html","content":"<span class='clj-double'>0.6489777022226878</span>","value":"0.6489777022226878"},{"type":"html","content":"<span class='clj-double'>0.582925598145255</span>","value":"0.582925598145255"},{"type":"html","content":"<span class='clj-double'>0.8959569718513714</span>","value":"0.8959569718513714"},{"type":"html","content":"<span class='clj-double'>0.8836774274149366</span>","value":"0.8836774274149366"},{"type":"html","content":"<span class='clj-double'>0.998843457243667</span>","value":"0.998843457243667"},{"type":"html","content":"<span class='clj-double'>0.9527264291997151</span>","value":"0.9527264291997151"},{"type":"html","content":"<span class='clj-double'>0.5395577652953624</span>","value":"0.5395577652953624"},{"type":"html","content":"<span class='clj-double'>0.9426805504856206</span>","value":"0.9426805504856206"},{"type":"html","content":"<span class='clj-double'>0.9191859297453133</span>","value":"0.9191859297453133"},{"type":"html","content":"<span class='clj-double'>0.6722844820977262</span>","value":"0.6722844820977262"},{"type":"html","content":"<span class='clj-double'>0.9381883587265045</span>","value":"0.9381883587265045"},{"type":"html","content":"<span class='clj-double'>0.9269564484285421</span>","value":"0.9269564484285421"},{"type":"html","content":"<span class='clj-double'>0.9330257340508935</span>","value":"0.9330257340508935"},{"type":"html","content":"<span class='clj-double'>0.5552921182340989</span>","value":"0.5552921182340989"},{"type":"html","content":"<span class='clj-double'>0.654937547590012</span>","value":"0.654937547590012"},{"type":"html","content":"<span class='clj-double'>0.8559617274515946</span>","value":"0.8559617274515946"},{"type":"html","content":"<span class='clj-double'>0.9497931732016976</span>","value":"0.9497931732016976"},{"type":"html","content":"<span class='clj-double'>0.987115354349859</span>","value":"0.987115354349859"},{"type":"html","content":"<span class='clj-double'>0.9847043146860303</span>","value":"0.9847043146860303"},{"type":"html","content":"<span class='clj-double'>0.4806401656010091</span>","value":"0.4806401656010091"},{"type":"html","content":"<span class='clj-double'>0.1973004668103066</span>","value":"0.1973004668103066"},{"type":"html","content":"<span class='clj-double'>0.6140888589303257</span>","value":"0.6140888589303257"},{"type":"html","content":"<span class='clj-double'>0.9754528545231328</span>","value":"0.9754528545231328"},{"type":"html","content":"<span class='clj-double'>0.888823978116453</span>","value":"0.888823978116453"},{"type":"html","content":"<span class='clj-double'>0.9764404127267813</span>","value":"0.9764404127267813"},{"type":"html","content":"<span class='clj-double'>0.9258578352891842</span>","value":"0.9258578352891842"},{"type":"html","content":"<span class='clj-double'>0.701370139620835</span>","value":"0.701370139620835"},{"type":"html","content":"<span class='clj-double'>0.4195910157024598</span>","value":"0.4195910157024598"},{"type":"html","content":"<span class='clj-double'>0.7103739331315426</span>","value":"0.7103739331315426"},{"type":"html","content":"<span class='clj-double'>0.8836774274149366</span>","value":"0.8836774274149366"}],"value":"(0.6087566360696021 0.6768629394473786 0.7584904460310258 0.9931969199616333 0.9330257340508935 0.9662778914818718 0.645261837312593 0.6396682784891242 0.8804740960553241 0.9847043146860303 0.986246099797052 0.9258578352891842 0.24865712052416133 0.11273295381672688 0.49529992378304655 0.4530238699283764 0.987115354349859 0.9931969199616333 0.9258578352891842 0.73434798984122 0.7862729314132327 0.6890085796827388 0.9764404127267813 0.952975945927778 0.874354657077925 0.6921608565052283 0.5717502197634339 0.8653550485295183 0.888823978116453 0.9527264291997151 0.952975945927778 0.9336988696984793 0.7253472974932383 0.6373237313421208 0.8418257774776928 0.998843457243667 0.9736678074034607 0.9928872832621787 0.7631225122699464 0.8810807526847219 0.1436010690358218 0.9786540653923581 0.986246099797052 0.9330257340508935 0.5027605496853293 0.04920075071576188 0.9991163543599261 0.9149976677073679 0.987115354349859 0.9847043146860303 0.9258578352891842 0.7088609366404914 0.8698327235962412 0.44027636454205354 0.9991163543599261 0.9764404127267813 0.9736678074034607 0.9714885968528271 0.8308051734438379 0.34555716547882465 0.9754528545231328 0.998843457243667 0.9527264291997151 0.9258578352891842 0.7497312447706685 0.5637059685254562 0.2863142580705646 0.9991163543599261 0.9764404127267813 0.9330257340508935 0.6489777022226878 0.582925598145255 0.8959569718513714 0.8836774274149366 0.998843457243667 0.9527264291997151 0.5395577652953624 0.9426805504856206 0.9191859297453133 0.6722844820977262 0.9381883587265045 0.9269564484285421 0.9330257340508935 0.5552921182340989 0.654937547590012 0.8559617274515946 0.9497931732016976 0.987115354349859 0.9847043146860303 0.4806401656010091 0.1973004668103066 0.6140888589303257 0.9754528545231328 0.888823978116453 0.9764404127267813 0.9258578352891842 0.701370139620835 0.4195910157024598 0.7103739331315426 0.8836774274149366)"}
;; <=

;; @@
>>>>>>> 63a59c8f566d41f08d41423a3a72a55eee8f663c
>>>>>>> 5fabb5d2320c0e3cfe2b92108c05cde5fe6aaf08
(def trans-mean (/ (reduce + 0.0 trans-samples) (count trans-samples)))

(defquery inference-fin [probs trans]
  (loop [result '()
         ps probs]
    (if (empty? ps)
      result
      (let [p-con (* (first ps) trans)
            is-con (if (sample (flip p-con)) 1 0)]
        (recur (cons is-con result)
               (rest ps))))))

(def final-result
	(first
      (map :result
     	(take 1 (take-nth
<<<<<<< HEAD
        	       50 (drop 1000 (doquery
                                   :ipmcmc
                                   inference-fin
                                   [probs  trans-mean])))))))
=======
<<<<<<< HEAD
        	       50 (drop 1000 (doquery
                                   :ipmcmc
                                   inference-fin
                                   [probs  trans-mean])))))))
=======
        	       50 (drop 5000 (doquery
                                   :ipmcmc
                                   inference-fin
                                   [probs trans-mean])))))))
>>>>>>> 63a59c8f566d41f08d41423a3a72a55eee8f663c
>>>>>>> 5fabb5d2320c0e3cfe2b92108c05cde5fe6aaf08
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/trans-mean</span>","value":"#'PROJECT/trans-mean"},{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/inference-fin</span>","value":"#'PROJECT/inference-fin"}],"value":"[#'PROJECT/trans-mean,#'PROJECT/inference-fin]"},{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/final-result</span>","value":"#'PROJECT/final-result"}],"value":"[[#'PROJECT/trans-mean,#'PROJECT/inference-fin],#'PROJECT/final-result]"}
;; <=

;; @@
(reduce + 0 final-result)
;; @@
;; =>
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 5fabb5d2320c0e3cfe2b92108c05cde5fe6aaf08
;;; {"type":"html","content":"<span class='clj-long'>148</span>","value":"148"}
;; <=

;; @@
(filter (fn [x] (= (nth final-result x) 1)) (range 0 (count members)))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"},{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"},{"type":"html","content":"<span class='clj-long'>8</span>","value":"8"},{"type":"html","content":"<span class='clj-long'>11</span>","value":"11"},{"type":"html","content":"<span class='clj-long'>12</span>","value":"12"},{"type":"html","content":"<span class='clj-long'>15</span>","value":"15"},{"type":"html","content":"<span class='clj-long'>18</span>","value":"18"},{"type":"html","content":"<span class='clj-long'>20</span>","value":"20"},{"type":"html","content":"<span class='clj-long'>21</span>","value":"21"},{"type":"html","content":"<span class='clj-long'>23</span>","value":"23"},{"type":"html","content":"<span class='clj-long'>27</span>","value":"27"},{"type":"html","content":"<span class='clj-long'>29</span>","value":"29"},{"type":"html","content":"<span class='clj-long'>32</span>","value":"32"},{"type":"html","content":"<span class='clj-long'>33</span>","value":"33"},{"type":"html","content":"<span class='clj-long'>37</span>","value":"37"},{"type":"html","content":"<span class='clj-long'>38</span>","value":"38"},{"type":"html","content":"<span class='clj-long'>39</span>","value":"39"},{"type":"html","content":"<span class='clj-long'>43</span>","value":"43"},{"type":"html","content":"<span class='clj-long'>45</span>","value":"45"},{"type":"html","content":"<span class='clj-long'>48</span>","value":"48"},{"type":"html","content":"<span class='clj-long'>50</span>","value":"50"},{"type":"html","content":"<span class='clj-long'>51</span>","value":"51"},{"type":"html","content":"<span class='clj-long'>52</span>","value":"52"},{"type":"html","content":"<span class='clj-long'>58</span>","value":"58"},{"type":"html","content":"<span class='clj-long'>60</span>","value":"60"},{"type":"html","content":"<span class='clj-long'>63</span>","value":"63"},{"type":"html","content":"<span class='clj-long'>64</span>","value":"64"},{"type":"html","content":"<span class='clj-long'>66</span>","value":"66"},{"type":"html","content":"<span class='clj-long'>69</span>","value":"69"},{"type":"html","content":"<span class='clj-long'>70</span>","value":"70"},{"type":"html","content":"<span class='clj-long'>72</span>","value":"72"},{"type":"html","content":"<span class='clj-long'>73</span>","value":"73"},{"type":"html","content":"<span class='clj-long'>77</span>","value":"77"},{"type":"html","content":"<span class='clj-long'>78</span>","value":"78"},{"type":"html","content":"<span class='clj-long'>80</span>","value":"80"},{"type":"html","content":"<span class='clj-long'>81</span>","value":"81"},{"type":"html","content":"<span class='clj-long'>82</span>","value":"82"},{"type":"html","content":"<span class='clj-long'>84</span>","value":"84"},{"type":"html","content":"<span class='clj-long'>87</span>","value":"87"},{"type":"html","content":"<span class='clj-long'>89</span>","value":"89"},{"type":"html","content":"<span class='clj-long'>90</span>","value":"90"},{"type":"html","content":"<span class='clj-long'>91</span>","value":"91"},{"type":"html","content":"<span class='clj-long'>94</span>","value":"94"},{"type":"html","content":"<span class='clj-long'>97</span>","value":"97"},{"type":"html","content":"<span class='clj-long'>100</span>","value":"100"},{"type":"html","content":"<span class='clj-long'>101</span>","value":"101"},{"type":"html","content":"<span class='clj-long'>103</span>","value":"103"},{"type":"html","content":"<span class='clj-long'>105</span>","value":"105"},{"type":"html","content":"<span class='clj-long'>106</span>","value":"106"},{"type":"html","content":"<span class='clj-long'>109</span>","value":"109"},{"type":"html","content":"<span class='clj-long'>110</span>","value":"110"},{"type":"html","content":"<span class='clj-long'>111</span>","value":"111"},{"type":"html","content":"<span class='clj-long'>113</span>","value":"113"},{"type":"html","content":"<span class='clj-long'>114</span>","value":"114"},{"type":"html","content":"<span class='clj-long'>115</span>","value":"115"},{"type":"html","content":"<span class='clj-long'>116</span>","value":"116"},{"type":"html","content":"<span class='clj-long'>119</span>","value":"119"},{"type":"html","content":"<span class='clj-long'>120</span>","value":"120"},{"type":"html","content":"<span class='clj-long'>122</span>","value":"122"},{"type":"html","content":"<span class='clj-long'>130</span>","value":"130"},{"type":"html","content":"<span class='clj-long'>133</span>","value":"133"},{"type":"html","content":"<span class='clj-long'>134</span>","value":"134"},{"type":"html","content":"<span class='clj-long'>135</span>","value":"135"},{"type":"html","content":"<span class='clj-long'>137</span>","value":"137"},{"type":"html","content":"<span class='clj-long'>140</span>","value":"140"},{"type":"html","content":"<span class='clj-long'>144</span>","value":"144"},{"type":"html","content":"<span class='clj-long'>147</span>","value":"147"},{"type":"html","content":"<span class='clj-long'>149</span>","value":"149"},{"type":"html","content":"<span class='clj-long'>151</span>","value":"151"},{"type":"html","content":"<span class='clj-long'>153</span>","value":"153"},{"type":"html","content":"<span class='clj-long'>157</span>","value":"157"},{"type":"html","content":"<span class='clj-long'>161</span>","value":"161"},{"type":"html","content":"<span class='clj-long'>163</span>","value":"163"},{"type":"html","content":"<span class='clj-long'>164</span>","value":"164"},{"type":"html","content":"<span class='clj-long'>165</span>","value":"165"},{"type":"html","content":"<span class='clj-long'>167</span>","value":"167"},{"type":"html","content":"<span class='clj-long'>172</span>","value":"172"},{"type":"html","content":"<span class='clj-long'>174</span>","value":"174"},{"type":"html","content":"<span class='clj-long'>175</span>","value":"175"},{"type":"html","content":"<span class='clj-long'>177</span>","value":"177"},{"type":"html","content":"<span class='clj-long'>181</span>","value":"181"},{"type":"html","content":"<span class='clj-long'>182</span>","value":"182"},{"type":"html","content":"<span class='clj-long'>183</span>","value":"183"},{"type":"html","content":"<span class='clj-long'>185</span>","value":"185"},{"type":"html","content":"<span class='clj-long'>186</span>","value":"186"},{"type":"html","content":"<span class='clj-long'>187</span>","value":"187"},{"type":"html","content":"<span class='clj-long'>189</span>","value":"189"},{"type":"html","content":"<span class='clj-long'>191</span>","value":"191"},{"type":"html","content":"<span class='clj-long'>192</span>","value":"192"},{"type":"html","content":"<span class='clj-long'>195</span>","value":"195"},{"type":"html","content":"<span class='clj-long'>199</span>","value":"199"},{"type":"html","content":"<span class='clj-long'>200</span>","value":"200"},{"type":"html","content":"<span class='clj-long'>201</span>","value":"201"},{"type":"html","content":"<span class='clj-long'>202</span>","value":"202"},{"type":"html","content":"<span class='clj-long'>203</span>","value":"203"},{"type":"html","content":"<span class='clj-long'>208</span>","value":"208"},{"type":"html","content":"<span class='clj-long'>211</span>","value":"211"},{"type":"html","content":"<span class='clj-long'>214</span>","value":"214"},{"type":"html","content":"<span class='clj-long'>215</span>","value":"215"},{"type":"html","content":"<span class='clj-long'>216</span>","value":"216"},{"type":"html","content":"<span class='clj-long'>218</span>","value":"218"},{"type":"html","content":"<span class='clj-long'>220</span>","value":"220"},{"type":"html","content":"<span class='clj-long'>221</span>","value":"221"},{"type":"html","content":"<span class='clj-long'>225</span>","value":"225"},{"type":"html","content":"<span class='clj-long'>226</span>","value":"226"},{"type":"html","content":"<span class='clj-long'>227</span>","value":"227"},{"type":"html","content":"<span class='clj-long'>228</span>","value":"228"},{"type":"html","content":"<span class='clj-long'>229</span>","value":"229"},{"type":"html","content":"<span class='clj-long'>230</span>","value":"230"},{"type":"html","content":"<span class='clj-long'>231</span>","value":"231"},{"type":"html","content":"<span class='clj-long'>235</span>","value":"235"},{"type":"html","content":"<span class='clj-long'>236</span>","value":"236"},{"type":"html","content":"<span class='clj-long'>238</span>","value":"238"},{"type":"html","content":"<span class='clj-long'>239</span>","value":"239"},{"type":"html","content":"<span class='clj-long'>243</span>","value":"243"},{"type":"html","content":"<span class='clj-long'>248</span>","value":"248"},{"type":"html","content":"<span class='clj-long'>249</span>","value":"249"},{"type":"html","content":"<span class='clj-long'>250</span>","value":"250"},{"type":"html","content":"<span class='clj-long'>254</span>","value":"254"},{"type":"html","content":"<span class='clj-long'>258</span>","value":"258"},{"type":"html","content":"<span class='clj-long'>259</span>","value":"259"},{"type":"html","content":"<span class='clj-long'>264</span>","value":"264"},{"type":"html","content":"<span class='clj-long'>265</span>","value":"265"},{"type":"html","content":"<span class='clj-long'>269</span>","value":"269"},{"type":"html","content":"<span class='clj-long'>271</span>","value":"271"},{"type":"html","content":"<span class='clj-long'>274</span>","value":"274"},{"type":"html","content":"<span class='clj-long'>275</span>","value":"275"},{"type":"html","content":"<span class='clj-long'>276</span>","value":"276"},{"type":"html","content":"<span class='clj-long'>280</span>","value":"280"},{"type":"html","content":"<span class='clj-long'>284</span>","value":"284"},{"type":"html","content":"<span class='clj-long'>285</span>","value":"285"},{"type":"html","content":"<span class='clj-long'>289</span>","value":"289"},{"type":"html","content":"<span class='clj-long'>290</span>","value":"290"},{"type":"html","content":"<span class='clj-long'>291</span>","value":"291"},{"type":"html","content":"<span class='clj-long'>294</span>","value":"294"},{"type":"html","content":"<span class='clj-long'>296</span>","value":"296"},{"type":"html","content":"<span class='clj-long'>297</span>","value":"297"},{"type":"html","content":"<span class='clj-long'>300</span>","value":"300"}],"value":"(0 1 2 3 4 8 11 12 15 18 20 21 23 27 29 32 33 37 38 39 43 45 48 50 51 52 58 60 63 64 66 69 70 72 73 77 78 80 81 82 84 87 89 90 91 94 97 100 101 103 105 106 109 110 111 113 114 115 116 119 120 122 130 133 134 135 137 140 144 147 149 151 153 157 161 163 164 165 167 172 174 175 177 181 182 183 185 186 187 189 191 192 195 199 200 201 202 203 208 211 214 215 216 218 220 221 225 226 227 228 229 230 231 235 236 238 239 243 248 249 250 254 258 259 264 265 269 271 274 275 276 280 284 285 289 290 291 294 296 297 300)"}
;; <=

;; @@
(dotimes [i (count members)]
  (do
    (print i)
    (print ": ")
    (println (nth members i))))
;; @@
;; ->
;;; 0: 金成泰
;;; 1: 강길부
;;; 2: 강병원
;;; 3: 강석진
;;; 4: 강석호
;;; 5: 강창일
;;; 6: 강효상
;;; 7: 강훈식
;;; 8: 경대수
;;; 9: 고용진
;;; 10: 곽대훈
;;; 11: 곽상도
;;; 12: 권미혁
;;; 13: 권석창
;;; 14: 권성동
;;; 15: 권은희
;;; 16: 권칠승
;;; 17: 금태섭
;;; 18: 기동민
;;; 19: 김경수
;;; 20: 김경진
;;; 21: 김경협
;;; 22: 김관영
;;; 23: 김광림
;;; 24: 김광수
;;; 25: 김규환
;;; 26: 김기선
;;; 27: 김도읍
;;; 28: 김동철
;;; 29: 김두관
;;; 30: 김명연
;;; 31: 김무성
;;; 32: 김민기
;;; 33: 김병관
;;; 34: 김병기
;;; 35: 김병욱
;;; 36: 김부겸
;;; 37: 김삼화
;;; 38: 김상훈
;;; 39: 김상희
;;; 40: 김석기
;;; 41: 김선동
;;; 42: 김성수
;;; 43: 김성식
;;; 44: 김성원
;;; 45: 김성찬
;;; 46: 김성태
;;; 47: 김세연
;;; 48: 김수민
;;; 49: 김순례
;;; 50: 김승희
;;; 51: 김영우
;;; 52: 김영주
;;; 53: 김영진
;;; 54: 김영춘
;;; 55: 김영호
;;; 56: 김용태
;;; 57: 김재경
;;; 58: 김재원
;;; 59: 김정우
;;; 60: 김정재
;;; 61: 김정훈
;;; 62: 김종대
;;; 63: 김종민
;;; 64: 김종석
;;; 65: 김종인
;;; 66: 김종태
;;; 67: 김종회
;;; 68: 김종훈
;;; 69: 김중로
;;; 70: 김진태
;;; 71: 김진표
;;; 72: 김철민
;;; 73: 김태년
;;; 74: 김태흠
;;; 75: 김학용
;;; 76: 김한정
;;; 77: 김한표
;;; 78: 김해영
;;; 79: 김현권
;;; 80: 김현미
;;; 81: 김현아
;;; 82: 나경원
;;; 83: 남인순
;;; 84: 노웅래
;;; 85: 노회찬
;;; 86: 도종환
;;; 87: 문미옥
;;; 88: 문진국
;;; 89: 문희상
;;; 90: 민경욱
;;; 91: 민병두
;;; 92: 민홍철
;;; 93: 박경미
;;; 94: 박광온
;;; 95: 박남춘
;;; 96: 박대출
;;; 97: 박덕흠
;;; 98: 박맹우
;;; 99: 박명재
;;; 100: 박범계
;;; 101: 박병석
;;; 102: 박선숙
;;; 103: 박성중
;;; 104: 박순자
;;; 105: 박영선
;;; 106: 박완수
;;; 107: 박완주
;;; 108: 박용진
;;; 109: 박인숙
;;; 110: 박재호
;;; 111: 박정
;;; 112: 박주민
;;; 113: 박주선
;;; 114: 박주현
;;; 115: 박준영
;;; 116: 박지원
;;; 117: 박찬대
;;; 118: 박찬우
;;; 119: 박홍근
;;; 120: 배덕광
;;; 121: 백승주
;;; 122: 백재현
;;; 123: 백혜련
;;; 124: 변재일
;;; 125: 서영교
;;; 126: 서청원
;;; 127: 서형수
;;; 128: 설훈
;;; 129: 성일종
;;; 130: 소병훈
;;; 131: 손금주
;;; 132: 손혜원
;;; 133: 송기석
;;; 134: 송기헌
;;; 135: 송석준
;;; 136: 송영길
;;; 137: 송옥주
;;; 138: 송희경
;;; 139: 신경민
;;; 140: 신동근
;;; 141: 신보라
;;; 142: 신상진
;;; 143: 신용현
;;; 144: 신창현
;;; 145: 심기준
;;; 146: 심상정
;;; 147: 심재권
;;; 148: 심재철
;;; 149: 안규백
;;; 150: 안민석
;;; 151: 안상수
;;; 152: 안철수
;;; 153: 안호영
;;; 154: 양승조
;;; 155: 어기구
;;; 156: 엄용수
;;; 157: 여상규
;;; 158: 염동열
;;; 159: 오세정
;;; 160: 오신환
;;; 161: 오영훈
;;; 162: 오제세
;;; 163: 우상호
;;; 164: 우원식
;;; 165: 원유철
;;; 166: 원혜영
;;; 167: 위성곤
;;; 168: 유기준
;;; 169: 유동수
;;; 170: 유민봉
;;; 171: 유성엽
;;; 172: 유승민
;;; 173: 유승희
;;; 174: 유은혜
;;; 175: 유의동
;;; 176: 유재중
;;; 177: 윤관석
;;; 178: 윤상직
;;; 179: 윤상현
;;; 180: 윤소하
;;; 181: 윤영석
;;; 182: 윤영일
;;; 183: 윤재옥
;;; 184: 윤종오
;;; 185: 윤종필
;;; 186: 윤한홍
;;; 187: 윤호중
;;; 188: 윤후덕
;;; 189: 이개호
;;; 190: 이군현
;;; 191: 이동섭
;;; 192: 이만희
;;; 193: 이명수
;;; 194: 이상돈
;;; 195: 이상민
;;; 196: 이석현
;;; 197: 이수혁
;;; 198: 이양수
;;; 199: 이언주
;;; 200: 이완영
;;; 201: 이용득
;;; 202: 이용주
;;; 203: 이용호
;;; 204: 이우현
;;; 205: 이원욱
;;; 206: 이은권
;;; 207: 이은재
;;; 208: 이인영
;;; 209: 이장우
;;; 210: 이재정
;;; 211: 이정미
;;; 212: 이정현
;;; 213: 이종걸
;;; 214: 이종구
;;; 215: 이종명
;;; 216: 이종배
;;; 217: 이주영
;;; 218: 이진복
;;; 219: 이찬열
;;; 220: 이채익
;;; 221: 이철규
;;; 222: 이철우
;;; 223: 이철희
;;; 224: 이춘석
;;; 225: 이태규
;;; 226: 이학영
;;; 227: 이학재
;;; 228: 이해찬
;;; 229: 이헌승
;;; 230: 이현재
;;; 231: 이혜훈
;;; 232: 이훈
;;; 233: 인재근
;;; 234: 임이자
;;; 235: 임종성
;;; 236: 장병완
;;; 237: 장석춘
;;; 238: 장정숙
;;; 239: 장제원
;;; 240: 전재수
;;; 241: 전해철
;;; 242: 전현희
;;; 243: 전혜숙
;;; 244: 전희경
;;; 245: 정갑윤
;;; 246: 정동영
;;; 247: 정병국
;;; 248: 정성호
;;; 249: 정세균
;;; 250: 정양석
;;; 251: 정용기
;;; 252: 정우택
;;; 253: 정운천
;;; 254: 정유섭
;;; 255: 정인화
;;; 256: 정재호
;;; 257: 정종섭
;;; 258: 정진석
;;; 259: 정춘숙
;;; 260: 정태옥
;;; 261: 제윤경
;;; 262: 조경태
;;; 263: 조배숙
;;; 264: 조승래
;;; 265: 조원진
;;; 266: 조응천
;;; 267: 조정식
;;; 268: 조훈현
;;; 269: 주광덕
;;; 270: 주승용
;;; 271: 주호영
;;; 272: 지상욱
;;; 273: 진선미
;;; 274: 진영
;;; 275: 채이배
;;; 276: 천정배
;;; 277: 최경환(평)
;;; 278: 최경환(한)
;;; 279: 최교일
;;; 280: 최도자
;;; 281: 최명길
;;; 282: 최연혜
;;; 283: 최운열
;;; 284: 최인호
;;; 285: 추경호
;;; 286: 추미애
;;; 287: 추혜선
;;; 288: 표창원
;;; 289: 하태경
;;; 290: 한선교
;;; 291: 한정애
;;; 292: 함진규
;;; 293: 홍문종
;;; 294: 홍문표
;;; 295: 홍영표
;;; 296: 홍의락
;;; 297: 홍익표
;;; 298: 홍일표
;;; 299: 홍철호
;;; 300: 황영철
;;; 301: 황주홍
;;; 302: 황희
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(cons 1 '())
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"(1)"}
;; <=

;; @@
(def raw-attendance (slurp "bayesian-ballot/data/0521_attendance_all.txt"))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/raw-attendance</span>","value":"#'PROJECT/raw-attendance"}
;; <=

;; @@
(require '[clojure.string :as str])
(def spt-attendance (str/split attendance #"\s+"))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/spt-attendance</span>","value":"#'PROJECT/spt-attendance"}],"value":"[nil,#'PROJECT/spt-attendance]"}
;; <=

;; @@
(def attendance '())
(loop [x spt-attendance
       i -1]
  (if (= (count (first x)) 1)
    (do
      (cons '() attendance)
      (recur [(rest x) (inc i)]))
    (do
      (cons (first x) (nth spt-attendance i))
      (recur [(rest x) i]))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;PROJECT/attendance</span>","value":"#'PROJECT/attendance"}
<<<<<<< HEAD
=======
=======
;;; {"type":"html","content":"<span class='clj-long'>131</span>","value":"131"}
>>>>>>> 63a59c8f566d41f08d41423a3a72a55eee8f663c
>>>>>>> 5fabb5d2320c0e3cfe2b92108c05cde5fe6aaf08
;; <=

;; @@

;; @@
