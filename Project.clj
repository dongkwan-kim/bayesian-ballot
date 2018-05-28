;; gorilla-repl.fileformat = 1

;; **
;;; # Gorilla REPL
;;; 
;; **

;; @@
(use 'nstools.ns)
(ns+ CS492_PROJECT
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
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/index-of</span>","value":"#'CS492_PROJECT/index-of"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/take-csv</span>","value":"#'CS492_PROJECT/take-csv"}],"value":"[#'CS492_PROJECT/index-of,#'CS492_PROJECT/take-csv]"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/vote_result_1521</span>","value":"#'CS492_PROJECT/vote_result_1521"}],"value":"[[#'CS492_PROJECT/index-of,#'CS492_PROJECT/take-csv],#'CS492_PROJECT/vote_result_1521]"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/vote_result</span>","value":"#'CS492_PROJECT/vote_result"}],"value":"[[[#'CS492_PROJECT/index-of,#'CS492_PROJECT/take-csv],#'CS492_PROJECT/vote_result_1521],#'CS492_PROJECT/vote_result]"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/members</span>","value":"#'CS492_PROJECT/members"}],"value":"[[[[#'CS492_PROJECT/index-of,#'CS492_PROJECT/take-csv],#'CS492_PROJECT/vote_result_1521],#'CS492_PROJECT/vote_result],#'CS492_PROJECT/members]"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/key-idx</span>","value":"#'CS492_PROJECT/key-idx"}],"value":"[[[[[#'CS492_PROJECT/index-of,#'CS492_PROJECT/take-csv],#'CS492_PROJECT/vote_result_1521],#'CS492_PROJECT/vote_result],#'CS492_PROJECT/members],#'CS492_PROJECT/key-idx]"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/encode-row</span>","value":"#'CS492_PROJECT/encode-row"}],"value":"[[[[[[#'CS492_PROJECT/index-of,#'CS492_PROJECT/take-csv],#'CS492_PROJECT/vote_result_1521],#'CS492_PROJECT/vote_result],#'CS492_PROJECT/members],#'CS492_PROJECT/key-idx],#'CS492_PROJECT/encode-row]"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/encoded-vote</span>","value":"#'CS492_PROJECT/encoded-vote"}],"value":"[[[[[[[#'CS492_PROJECT/index-of,#'CS492_PROJECT/take-csv],#'CS492_PROJECT/vote_result_1521],#'CS492_PROJECT/vote_result],#'CS492_PROJECT/members],#'CS492_PROJECT/key-idx],#'CS492_PROJECT/encode-row],#'CS492_PROJECT/encoded-vote]"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/vote-ith</span>","value":"#'CS492_PROJECT/vote-ith"}],"value":"[[[[[[[[#'CS492_PROJECT/index-of,#'CS492_PROJECT/take-csv],#'CS492_PROJECT/vote_result_1521],#'CS492_PROJECT/vote_result],#'CS492_PROJECT/members],#'CS492_PROJECT/key-idx],#'CS492_PROJECT/encode-row],#'CS492_PROJECT/encoded-vote],#'CS492_PROJECT/vote-ith]"}
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
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/sample-plot</span>","value":"#'CS492_PROJECT/sample-plot"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/sample-mean</span>","value":"#'CS492_PROJECT/sample-mean"}],"value":"[#'CS492_PROJECT/sample-plot,#'CS492_PROJECT/sample-mean]"}
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
    (map :result (take n (take-nth 50 (drop 1000 (doquery :ipmcmc ind-bat-opt [prob])))))))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/ind-bat-opt</span>","value":"#'CS492_PROJECT/ind-bat-opt"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/sample-prob</span>","value":"#'CS492_PROJECT/sample-prob"}],"value":"[#'CS492_PROJECT/ind-bat-opt,#'CS492_PROJECT/sample-prob]"}
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
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/encode-vec</span>","value":"#'CS492_PROJECT/encode-vec"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/encoded-vote-vec</span>","value":"#'CS492_PROJECT/encoded-vote-vec"}],"value":"[#'CS492_PROJECT/encode-vec,#'CS492_PROJECT/encoded-vote-vec]"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/vote-vec-ith</span>","value":"#'CS492_PROJECT/vote-vec-ith"}],"value":"[[#'CS492_PROJECT/encode-vec,#'CS492_PROJECT/encoded-vote-vec],#'CS492_PROJECT/vote-vec-ith]"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/dot-prod</span>","value":"#'CS492_PROJECT/dot-prod"}],"value":"[[[#'CS492_PROJECT/encode-vec,#'CS492_PROJECT/encoded-vote-vec],#'CS492_PROJECT/vote-vec-ith],#'CS492_PROJECT/dot-prod]"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/norm</span>","value":"#'CS492_PROJECT/norm"}],"value":"[[[[#'CS492_PROJECT/encode-vec,#'CS492_PROJECT/encoded-vote-vec],#'CS492_PROJECT/vote-vec-ith],#'CS492_PROJECT/dot-prod],#'CS492_PROJECT/norm]"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/cos-sim</span>","value":"#'CS492_PROJECT/cos-sim"}],"value":"[[[[[#'CS492_PROJECT/encode-vec,#'CS492_PROJECT/encoded-vote-vec],#'CS492_PROJECT/vote-vec-ith],#'CS492_PROJECT/dot-prod],#'CS492_PROJECT/norm],#'CS492_PROJECT/cos-sim]"}
;; <=

;; @@
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
(defquery inference [probs]
  (let [a (+ 1 (sample (poisson 1)))
        b (+ 1 (sample (poisson 1)))
        trans (sample (beta a b))]
    (reduce + 0 
            (map )
            (sample (flip (- 1 (* (nth probs )))))
;; @@
