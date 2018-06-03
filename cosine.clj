;; gorilla-repl.fileformat = 1

;; **
;;; ## Independent: Cosine Distance.
;; **

;; @@
(use 'nstools.ns)
(ns+ pro1
  (:like anglican-user.worksheet)
  (:require [clojure-csv.core :as csv]
            [clojure.java.io :as io]
            [clojure.string :as string]))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"[nil,nil]"}
;; <=

;; @@
(defn take-csv [fname] (with-open [file (io/reader fname)] (csv/parse-csv (slurp file))))
(defn index-of [item coll]
  (count (take-while (partial not= item) coll)))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;pro1/take-csv</span>","value":"#'pro1/take-csv"},{"type":"html","content":"<span class='clj-var'>#&#x27;pro1/index-of</span>","value":"#'pro1/index-of"}],"value":"[#'pro1/take-csv,#'pro1/index-of]"}
;; <=

;; @@
(defn take-csv
  [fname]
  (with-open [file (io/reader fname)]
    (csv/parse-csv (slurp file))))

; Files
(def vote_result_sample (take-csv "bayesian-ballot/data/vote_result_1521.csv"))
(def vote_result_1521 (take-csv "bayesian-ballot/data/vote_result_1521.csv"))

(def vote_result vote_result_sample)

; Key member
(def members (rest (first vote_result)))
(def key-idx (index-of "염동열" members))

; Encoding
(defn encode-row [vr-row]
  (let [key-choice (nth vr-row key-idx)]
    (map (fn [x] (= key-choice x)) vr-row)))

(def encoded-vote
  (map encode-row (filter
                    (fn [row] (not (= (nth row key-idx) "abs")))
                    (map rest (rest vote_result)))))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;pro1/take-csv</span>","value":"#'pro1/take-csv"},{"type":"html","content":"<span class='clj-var'>#&#x27;pro1/vote_result_sample</span>","value":"#'pro1/vote_result_sample"}],"value":"[#'pro1/take-csv,#'pro1/vote_result_sample]"},{"type":"html","content":"<span class='clj-var'>#&#x27;pro1/vote_result_1521</span>","value":"#'pro1/vote_result_1521"}],"value":"[[#'pro1/take-csv,#'pro1/vote_result_sample],#'pro1/vote_result_1521]"},{"type":"html","content":"<span class='clj-var'>#&#x27;pro1/vote_result</span>","value":"#'pro1/vote_result"}],"value":"[[[#'pro1/take-csv,#'pro1/vote_result_sample],#'pro1/vote_result_1521],#'pro1/vote_result]"},{"type":"html","content":"<span class='clj-var'>#&#x27;pro1/members</span>","value":"#'pro1/members"}],"value":"[[[[#'pro1/take-csv,#'pro1/vote_result_sample],#'pro1/vote_result_1521],#'pro1/vote_result],#'pro1/members]"},{"type":"html","content":"<span class='clj-var'>#&#x27;pro1/key-idx</span>","value":"#'pro1/key-idx"}],"value":"[[[[[#'pro1/take-csv,#'pro1/vote_result_sample],#'pro1/vote_result_1521],#'pro1/vote_result],#'pro1/members],#'pro1/key-idx]"},{"type":"html","content":"<span class='clj-var'>#&#x27;pro1/encode-row</span>","value":"#'pro1/encode-row"}],"value":"[[[[[[#'pro1/take-csv,#'pro1/vote_result_sample],#'pro1/vote_result_1521],#'pro1/vote_result],#'pro1/members],#'pro1/key-idx],#'pro1/encode-row]"},{"type":"html","content":"<span class='clj-var'>#&#x27;pro1/encoded-vote</span>","value":"#'pro1/encoded-vote"}],"value":"[[[[[[[#'pro1/take-csv,#'pro1/vote_result_sample],#'pro1/vote_result_1521],#'pro1/vote_result],#'pro1/members],#'pro1/key-idx],#'pro1/encode-row],#'pro1/encoded-vote]"}
;; <=

;; **
;;; ### Cosine Distance
;;; 
;;; Averaged over bills
;; **

;; @@
(defn encode-row3 [vr-row]
  (map (fn [x] (if (= "pro"     x) 1                   ; pro:   +1
                 (if (= "con"   x) -1                  ; con:   -1
                   (if (= "wdr" x) -0.5 0)))) vr-row)) ; wdr: -0.5, abs: ±0

(defn encode-col-by [vr-rows key-idx idx]
  (let [rows  (map encode-row3 vr-rows)
        col1  (map (fn [row] (nth row key-idx)) rows)  ; key
        col2  (map (fn [row] (nth row idx)) rows)] ; non-key
   (list col1 col2)))

(defn get-observations [key-idx idx]
	(encode-col-by (filter
                     (fn [row] (not (or (= (nth row key-idx) "abs") (= (nth row idx) "abs"))))
                       (map rest (rest vote_result))) key-idx idx))
(defn get-observations-with-abs [key-idx idx]
	(encode-col-by (map rest (rest vote_result)) key-idx idx))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;pro1/encode-row3</span>","value":"#'pro1/encode-row3"},{"type":"html","content":"<span class='clj-var'>#&#x27;pro1/encode-col-by</span>","value":"#'pro1/encode-col-by"}],"value":"[#'pro1/encode-row3,#'pro1/encode-col-by]"},{"type":"html","content":"<span class='clj-var'>#&#x27;pro1/get-observations</span>","value":"#'pro1/get-observations"}],"value":"[[#'pro1/encode-row3,#'pro1/encode-col-by],#'pro1/get-observations]"},{"type":"html","content":"<span class='clj-var'>#&#x27;pro1/get-observations-with-abs</span>","value":"#'pro1/get-observations-with-abs"}],"value":"[[[#'pro1/encode-row3,#'pro1/encode-col-by],#'pro1/get-observations],#'pro1/get-observations-with-abs]"}
;; <=

;; @@
(defn cosine-dist [x y]
  (let [norm (fn [x] (Math/sqrt (reduce + (map (fn [x] (* x x)) x))))
        nx   (norm x)
        ny   (norm y)
        dot  (reduce + (map (fn [x] (* (first x) (second x))) (map vector x y)))]
   (/ (/ dot nx) ny)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;pro1/cosine-dist</span>","value":"#'pro1/cosine-dist"}
;; <=

;; @@
(defn cosine-with-abs [a b]
  (let [z (get-observations-with-abs (index-of a members) (index-of b members))
        x (first  z)
        y (second z)]
   (cosine-dist x y)))
(defn cosine [a b]
  (let [z (get-observations (index-of a members) (index-of b members))
        x (first  z)
        y (second z)]
   (cosine-dist x y)))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;pro1/cosine-with-abs</span>","value":"#'pro1/cosine-with-abs"},{"type":"html","content":"<span class='clj-var'>#&#x27;pro1/cosine</span>","value":"#'pro1/cosine"}],"value":"[#'pro1/cosine-with-abs,#'pro1/cosine]"}
;; <=

;; @@
(print (cosine-with-abs "이은권" "홍문종") "\n")
(print (cosine-with-abs "김성찬" "홍문종") "\n")
(print (cosine-with-abs "진영" "홍문종") "\n")
(print (cosine "이은권" "홍문종") "\n")
;; @@
;; ->
;;; 0.4788986639225387 
;;; 0.4802357439865026 
;;; 0.4781337108513261 
;;; 0.7997330905991579 
;;; 
;; <-
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"[nil,nil]"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"[[nil,nil],nil]"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"[[[nil,nil],nil],nil]"}
;; <=

;; @@
(defn farthest-k-with-abs [target k]
  (let [name-dist-pairs (map vector members (map (fn [x] (cosine-with-abs target x)) members))
        sorted-pairs    (sort-by second name-dist-pairs)
        top-k           (take k sorted-pairs)]
   top-k))
(defn farthest-k [target k]
  (let [name-dist-pairs (map vector members (map (fn [x] (cosine target x)) members))
        sorted-pairs    (sort-by second name-dist-pairs)
        top-k           (take k sorted-pairs)]
   top-k))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;pro1/farthest-k-with-abs</span>","value":"#'pro1/farthest-k-with-abs"},{"type":"html","content":"<span class='clj-var'>#&#x27;pro1/farthest-k</span>","value":"#'pro1/farthest-k"}],"value":"[#'pro1/farthest-k-with-abs,#'pro1/farthest-k]"}
;; <=

;; @@
(print "with absent:\n")
(print (string/join "\n"(map (fn [x] (string/join "\t\t" (list (str (first x)) (str (second x))))) (farthest-k-with-abs "홍문종" 30))))
(print "\nwithout absent:\n")
(print (string/join "\n" (map (fn [x] (string/join "\t\t" (list (str (first x)) (str (second x))))) (farthest-k "홍문종" 30))))
;; @@
;; ->
;;; with absent:
;;; 김용태		0.08705189317243592
;;; 서청원		0.10689054070982773
;;; 김종태		0.13494238979022935
;;; 최경환(한)		0.16969895171356889
;;; 윤영석		0.1857676384629845
;;; 김재원		0.18672864021540103
;;; 심기준		0.20626511035380587
;;; 이혜훈		0.21600997032578034
;;; 홍문표		0.22898978349138005
;;; 김종인		0.2324732728066301
;;; 이종걸		0.23873909203245786
;;; 박맹우		0.24009598581635228
;;; 배덕광		0.24156296114456877
;;; 안철수		0.24418873859913434
;;; 원유철		0.24988903577991206
;;; 권은희		0.24998756132342317
;;; 이수혁		0.26071709155585415
;;; 한선교		0.26615175360242044
;;; 정병국		0.2668072812331876
;;; 여상규		0.2670247965762373
;;; 김석기		0.2719235323622125
;;; 김태년		0.27322586425267853
;;; 전재수		0.2783276461632211
;;; 이언주		0.2812610768448154
;;; 변재일		0.28170769636732806
;;; 정세균		0.28186135991682804
;;; 강효상		0.28216643368629996
;;; 윤상현		0.28356817084272073
;;; 송기석		0.28368707584084557
;;; 김현아		0.28455456384709077
;;; without absent:
;;; 김용태		0.5164854031588513
;;; 김재원		0.5237043561299353
;;; 강효상		0.6628761796830304
;;; 윤영석		0.6770786198930964
;;; 곽상도		0.6792664826745728
;;; 이종걸		0.6844619976647217
;;; 김종인		0.7013069356548975
;;; 최경환(한)		0.7019223584630867
;;; 홍익표		0.7034317065303928
;;; 김현아		0.7037795015497751
;;; 윤소하		0.7053839063177257
;;; 채이배		0.7135414748299301
;;; 김종대		0.7153542227466154
;;; 정세균		0.7174656525042056
;;; 윤한홍		0.723336092217193
;;; 주호영		0.7240768675858964
;;; 김무성		0.7247641686879914
;;; 김태흠		0.7277819064523812
;;; 이상돈		0.728496104244259
;;; 박맹우		0.7298987605803101
;;; 이언주		0.7303859314948647
;;; 정동영		0.7325020876185768
;;; 이용호		0.733874164259151
;;; 이태규		0.7352425507599079
;;; 하태경		0.7364676367751749
;;; 서청원		0.7397319207592136
;;; 박성중		0.7398287580118957
;;; 송기헌		0.7406541653570685
;;; 추혜선		0.7408238003707762
;;; 홍영표		0.7412062544606146
;; <-
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"[nil,nil]"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"[[nil,nil],nil]"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"[[[nil,nil],nil],nil]"}
;; <=

;; @@
(print "with absent:\n")
(print (string/join "\n"(map (fn [x] (string/join "\t\t" (list (str (first x)) (str (second x))))) (farthest-k-with-abs "염동열" 30))))
(print "\nwithout absent:\n")
(print (string/join "\n" (map (fn [x] (string/join "\t\t" (list (str (first x)) (str (second x))))) (farthest-k "염동열" 30))))
;; @@
;; ->
;;; with absent:
;;; 김종인		0.22341433740926955
;;; 서청원		0.24144689595433674
;;; 김종태		0.3353338414204857
;;; 최경환(한)		0.3461674101907095
;;; 윤영석		0.39261186984740004
;;; 배덕광		0.39378360973368026
;;; 김용태		0.4085145011863606
;;; 김재원		0.4106676310825633
;;; 홍문종		0.4412900747301585
;;; 이해찬		0.4562788342962867
;;; 김현미		0.47043621920041145
;;; 안철수		0.4718581322970182
;;; 박맹우		0.47550547361101814
;;; 여상규		0.4841676223580458
;;; 도종환		0.48622117578054286
;;; 윤한홍		0.49528318180282827
;;; 성일종		0.5151529737079366
;;; 문미옥		0.5155335934112766
;;; 강효상		0.5208516947564027
;;; 이우현		0.527282412262382
;;; 김부겸		0.5365495704528028
;;; 권석창		0.5386420860935489
;;; 김영주		0.5394226405361185
;;; 이장우		0.5444622198298358
;;; 이혜훈		0.5453618961751451
;;; 김석기		0.5461797723034144
;;; 이태규		0.5507601405495097
;;; 김태년		0.5551621857280435
;;; 변재일		0.5558963553639934
;;; 원유철		0.5581669652777856
;;; without absent:
;;; 강효상		0.7150972106532039
;;; 박성중		0.7567519164561499
;;; 곽상도		0.766744976343939
;;; 곽대훈		0.7954093811857463
;;; 윤한홍		0.7958666771501846
;;; 김용태		0.809063026244476
;;; 홍문종		0.8126202307087635
;;; 문미옥		0.8251353773670228
;;; 최명길		0.8278197078999682
;;; 박대출		0.8345786201673691
;;; 백승주		0.8346915977387571
;;; 전희경		0.8362678025334183
;;; 성일종		0.8362925180621122
;;; 이상돈		0.8376688042255305
;;; 김종인		0.8398629694762516
;;; 김재원		0.8425311426992093
;;; 주호영		0.8439285337426904
;;; 이장우		0.8554927426588795
;;; 이훈		0.8556711835291233
;;; 이정미		0.8558796962234592
;;; 이재정		0.8568106477798955
;;; 이태규		0.8599802000074112
;;; 조원진		0.8619440013424756
;;; 홍익표		0.8640736355340313
;;; 이현재		0.8668287644609473
;;; 추경호		0.8717816291197842
;;; 송기헌		0.87183576048849
;;; 추혜선		0.873398490925916
;;; 김태흠		0.8761412957470778
;;; 최경환(한)		0.8762369318878662
;; <-
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"[nil,nil]"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"[[nil,nil],nil]"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"[[[nil,nil],nil],nil]"}
;; <=
