;; gorilla-repl.fileformat = 1

;; **
;;; ## Explore
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

;; **
;;; @@\newcommand{\P}{{\text{P}}} \newcommand{\E}{{\mathbb{E}}}@@
;;; @@\newcommand{\invn}{\frac{1}{n}} @@
;;; @@\newcommand{\sin}{\sum\_{i=1}^n}@@
;;; @@\newcommand{\V}{\text{Var}}@@
;; **

;; @@
(defn take-csv [fname] (with-open [file (io/reader fname)] (csv/parse-csv (slurp file))))
(defn index-of [item coll]
  (count (take-while (partial not= item) coll)))

;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;pro1/take-csv</span>","value":"#'pro1/take-csv"},{"type":"html","content":"<span class='clj-var'>#&#x27;pro1/index-of</span>","value":"#'pro1/index-of"}],"value":"[#'pro1/take-csv,#'pro1/index-of]"}
;; <=

;; **
;;; ### 1521 Rows (first row: name)
;; **

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

;; @@
(rest (first vote_result))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>(&quot;金成泰&quot; &quot;강길부&quot; &quot;강병원&quot; &quot;강석진&quot; &quot;강석호&quot; &quot;강창일&quot; &quot;강효상&quot; &quot;강훈식&quot; &quot;경대수&quot; &quot;고용진&quot; &quot;곽대훈&quot; &quot;곽상도&quot; &quot;권미혁&quot; &quot;권석창&quot; &quot;권성동&quot; &quot;권은희&quot; &quot;권칠승&quot; &quot;금태섭&quot; &quot;기동민&quot; &quot;김경수&quot; &quot;김경진&quot; &quot;김경협&quot; &quot;김관영&quot; &quot;김광림&quot; &quot;김광수&quot; &quot;김규환&quot; &quot;김기선&quot; &quot;김도읍&quot; &quot;김동철&quot; &quot;김두관&quot; &quot;김명연&quot; &quot;김무성&quot; &quot;김민기&quot; &quot;김병관&quot; &quot;김병기&quot; &quot;김병욱&quot; &quot;김부겸&quot; &quot;김삼화&quot; &quot;김상훈&quot; &quot;김상희&quot; &quot;김석기&quot; &quot;김선동&quot; &quot;김성수&quot; &quot;김성식&quot; &quot;김성원&quot; &quot;김성찬&quot; &quot;김성태&quot; &quot;김세연&quot; &quot;김수민&quot; &quot;김순례&quot; &quot;김승희&quot; &quot;김영우&quot; &quot;김영주&quot; &quot;김영진&quot; &quot;김영춘&quot; &quot;김영호&quot; &quot;김용태&quot; &quot;김재경&quot; &quot;김재원&quot; &quot;김정우&quot; &quot;김정재&quot; &quot;김정훈&quot; &quot;김종대&quot; &quot;김종민&quot; &quot;김종석&quot; &quot;김종인&quot; &quot;김종태&quot; &quot;김종회&quot; &quot;김종훈&quot; &quot;김중로&quot; &quot;김진태&quot; &quot;김진표&quot; &quot;김철민&quot; &quot;김태년&quot; &quot;김태흠&quot; &quot;김학용&quot; &quot;김한정&quot; &quot;김한표&quot; &quot;김해영&quot; &quot;김현권&quot; &quot;김현미&quot; &quot;김현아&quot; &quot;나경원&quot; &quot;남인순&quot; &quot;노웅래&quot; &quot;노회찬&quot; &quot;도종환&quot; &quot;문미옥&quot; &quot;문진국&quot; &quot;문희상&quot; &quot;민경욱&quot; &quot;민병두&quot; &quot;민홍철&quot; &quot;박경미&quot; &quot;박광온&quot; &quot;박남춘&quot; &quot;박대출&quot; &quot;박덕흠&quot; &quot;박맹우&quot; &quot;박명재&quot; &quot;박범계&quot; &quot;박병석&quot; &quot;박선숙&quot; &quot;박성중&quot; &quot;박순자&quot; &quot;박영선&quot; &quot;박완수&quot; &quot;박완주&quot; &quot;박용진&quot; &quot;박인숙&quot; &quot;박재호&quot; &quot;박정&quot; &quot;박주민&quot; &quot;박주선&quot; &quot;박주현&quot; &quot;박준영&quot; &quot;박지원&quot; &quot;박찬대&quot; &quot;박찬우&quot; &quot;박홍근&quot; &quot;배덕광&quot; &quot;백승주&quot; &quot;백재현&quot; &quot;백혜련&quot; &quot;변재일&quot; &quot;서영교&quot; &quot;서청원&quot; &quot;서형수&quot; &quot;설훈&quot; &quot;성일종&quot; &quot;소병훈&quot; &quot;손금주&quot; &quot;손혜원&quot; &quot;송기석&quot; &quot;송기헌&quot; &quot;송석준&quot; &quot;송영길&quot; &quot;송옥주&quot; &quot;송희경&quot; &quot;신경민&quot; &quot;신동근&quot; &quot;신보라&quot; &quot;신상진&quot; &quot;신용현&quot; &quot;신창현&quot; &quot;심기준&quot; &quot;심상정&quot; &quot;심재권&quot; &quot;심재철&quot; &quot;안규백&quot; &quot;안민석&quot; &quot;안상수&quot; &quot;안철수&quot; &quot;안호영&quot; &quot;양승조&quot; &quot;어기구&quot; &quot;엄용수&quot; &quot;여상규&quot; &quot;염동열&quot; &quot;오세정&quot; &quot;오신환&quot; &quot;오영훈&quot; &quot;오제세&quot; &quot;우상호&quot; &quot;우원식&quot; &quot;원유철&quot; &quot;원혜영&quot; &quot;위성곤&quot; &quot;유기준&quot; &quot;유동수&quot; &quot;유민봉&quot; &quot;유성엽&quot; &quot;유승민&quot; &quot;유승희&quot; &quot;유은혜&quot; &quot;유의동&quot; &quot;유재중&quot; &quot;윤관석&quot; &quot;윤상직&quot; &quot;윤상현&quot; &quot;윤소하&quot; &quot;윤영석&quot; &quot;윤영일&quot; &quot;윤재옥&quot; &quot;윤종오&quot; &quot;윤종필&quot; &quot;윤한홍&quot; &quot;윤호중&quot; &quot;윤후덕&quot; &quot;이개호&quot; &quot;이군현&quot; &quot;이동섭&quot; &quot;이만희&quot; &quot;이명수&quot; &quot;이상돈&quot; &quot;이상민&quot; &quot;이석현&quot; &quot;이수혁&quot; &quot;이양수&quot; &quot;이언주&quot; &quot;이완영&quot; &quot;이용득&quot; &quot;이용주&quot; &quot;이용호&quot; &quot;이우현&quot; &quot;이원욱&quot; &quot;이은권&quot; &quot;이은재&quot; &quot;이인영&quot; &quot;이장우&quot; &quot;이재정&quot; &quot;이정미&quot; &quot;이정현&quot; &quot;이종걸&quot; &quot;이종구&quot; &quot;이종명&quot; &quot;이종배&quot; &quot;이주영&quot; &quot;이진복&quot; &quot;이찬열&quot; &quot;이채익&quot; &quot;이철규&quot; &quot;이철우&quot; &quot;이철희&quot; &quot;이춘석&quot; &quot;이태규&quot; &quot;이학영&quot; &quot;이학재&quot; &quot;이해찬&quot; &quot;이헌승&quot; &quot;이현재&quot; &quot;이혜훈&quot; &quot;이훈&quot; &quot;인재근&quot; &quot;임이자&quot; &quot;임종성&quot; &quot;장병완&quot; &quot;장석춘&quot; &quot;장정숙&quot; &quot;장제원&quot; &quot;전재수&quot; &quot;전해철&quot; &quot;전현희&quot; &quot;전혜숙&quot; &quot;전희경&quot; &quot;정갑윤&quot; &quot;정동영&quot; &quot;정병국&quot; &quot;정성호&quot; &quot;정세균&quot; &quot;정양석&quot; &quot;정용기&quot; &quot;정우택&quot; &quot;정운천&quot; &quot;정유섭&quot; &quot;정인화&quot; &quot;정재호&quot; &quot;정종섭&quot; &quot;정진석&quot; &quot;정춘숙&quot; &quot;정태옥&quot; &quot;제윤경&quot; &quot;조경태&quot; &quot;조배숙&quot; &quot;조승래&quot; &quot;조원진&quot; &quot;조응천&quot; &quot;조정식&quot; &quot;조훈현&quot; &quot;주광덕&quot; &quot;주승용&quot; &quot;주호영&quot; &quot;지상욱&quot; &quot;진선미&quot; &quot;진영&quot; &quot;채이배&quot; &quot;천정배&quot; &quot;최경환(평)&quot; &quot;최경환(한)&quot; &quot;최교일&quot; &quot;최도자&quot; &quot;최명길&quot; &quot;최연혜&quot; &quot;최운열&quot; &quot;최인호&quot; &quot;추경호&quot; &quot;추미애&quot; &quot;추혜선&quot; &quot;표창원&quot; &quot;하태경&quot; &quot;한선교&quot; &quot;한정애&quot; &quot;함진규&quot; &quot;홍문종&quot; &quot;홍문표&quot; &quot;홍영표&quot; &quot;홍의락&quot; &quot;홍익표&quot; &quot;홍일표&quot; &quot;홍철호&quot; &quot;황영철&quot; &quot;황주홍&quot; &quot;황희&quot;)</span>","value":"(\"金成泰\" \"강길부\" \"강병원\" \"강석진\" \"강석호\" \"강창일\" \"강효상\" \"강훈식\" \"경대수\" \"고용진\" \"곽대훈\" \"곽상도\" \"권미혁\" \"권석창\" \"권성동\" \"권은희\" \"권칠승\" \"금태섭\" \"기동민\" \"김경수\" \"김경진\" \"김경협\" \"김관영\" \"김광림\" \"김광수\" \"김규환\" \"김기선\" \"김도읍\" \"김동철\" \"김두관\" \"김명연\" \"김무성\" \"김민기\" \"김병관\" \"김병기\" \"김병욱\" \"김부겸\" \"김삼화\" \"김상훈\" \"김상희\" \"김석기\" \"김선동\" \"김성수\" \"김성식\" \"김성원\" \"김성찬\" \"김성태\" \"김세연\" \"김수민\" \"김순례\" \"김승희\" \"김영우\" \"김영주\" \"김영진\" \"김영춘\" \"김영호\" \"김용태\" \"김재경\" \"김재원\" \"김정우\" \"김정재\" \"김정훈\" \"김종대\" \"김종민\" \"김종석\" \"김종인\" \"김종태\" \"김종회\" \"김종훈\" \"김중로\" \"김진태\" \"김진표\" \"김철민\" \"김태년\" \"김태흠\" \"김학용\" \"김한정\" \"김한표\" \"김해영\" \"김현권\" \"김현미\" \"김현아\" \"나경원\" \"남인순\" \"노웅래\" \"노회찬\" \"도종환\" \"문미옥\" \"문진국\" \"문희상\" \"민경욱\" \"민병두\" \"민홍철\" \"박경미\" \"박광온\" \"박남춘\" \"박대출\" \"박덕흠\" \"박맹우\" \"박명재\" \"박범계\" \"박병석\" \"박선숙\" \"박성중\" \"박순자\" \"박영선\" \"박완수\" \"박완주\" \"박용진\" \"박인숙\" \"박재호\" \"박정\" \"박주민\" \"박주선\" \"박주현\" \"박준영\" \"박지원\" \"박찬대\" \"박찬우\" \"박홍근\" \"배덕광\" \"백승주\" \"백재현\" \"백혜련\" \"변재일\" \"서영교\" \"서청원\" \"서형수\" \"설훈\" \"성일종\" \"소병훈\" \"손금주\" \"손혜원\" \"송기석\" \"송기헌\" \"송석준\" \"송영길\" \"송옥주\" \"송희경\" \"신경민\" \"신동근\" \"신보라\" \"신상진\" \"신용현\" \"신창현\" \"심기준\" \"심상정\" \"심재권\" \"심재철\" \"안규백\" \"안민석\" \"안상수\" \"안철수\" \"안호영\" \"양승조\" \"어기구\" \"엄용수\" \"여상규\" \"염동열\" \"오세정\" \"오신환\" \"오영훈\" \"오제세\" \"우상호\" \"우원식\" \"원유철\" \"원혜영\" \"위성곤\" \"유기준\" \"유동수\" \"유민봉\" \"유성엽\" \"유승민\" \"유승희\" \"유은혜\" \"유의동\" \"유재중\" \"윤관석\" \"윤상직\" \"윤상현\" \"윤소하\" \"윤영석\" \"윤영일\" \"윤재옥\" \"윤종오\" \"윤종필\" \"윤한홍\" \"윤호중\" \"윤후덕\" \"이개호\" \"이군현\" \"이동섭\" \"이만희\" \"이명수\" \"이상돈\" \"이상민\" \"이석현\" \"이수혁\" \"이양수\" \"이언주\" \"이완영\" \"이용득\" \"이용주\" \"이용호\" \"이우현\" \"이원욱\" \"이은권\" \"이은재\" \"이인영\" \"이장우\" \"이재정\" \"이정미\" \"이정현\" \"이종걸\" \"이종구\" \"이종명\" \"이종배\" \"이주영\" \"이진복\" \"이찬열\" \"이채익\" \"이철규\" \"이철우\" \"이철희\" \"이춘석\" \"이태규\" \"이학영\" \"이학재\" \"이해찬\" \"이헌승\" \"이현재\" \"이혜훈\" \"이훈\" \"인재근\" \"임이자\" \"임종성\" \"장병완\" \"장석춘\" \"장정숙\" \"장제원\" \"전재수\" \"전해철\" \"전현희\" \"전혜숙\" \"전희경\" \"정갑윤\" \"정동영\" \"정병국\" \"정성호\" \"정세균\" \"정양석\" \"정용기\" \"정우택\" \"정운천\" \"정유섭\" \"정인화\" \"정재호\" \"정종섭\" \"정진석\" \"정춘숙\" \"정태옥\" \"제윤경\" \"조경태\" \"조배숙\" \"조승래\" \"조원진\" \"조응천\" \"조정식\" \"조훈현\" \"주광덕\" \"주승용\" \"주호영\" \"지상욱\" \"진선미\" \"진영\" \"채이배\" \"천정배\" \"최경환(평)\" \"최경환(한)\" \"최교일\" \"최도자\" \"최명길\" \"최연혜\" \"최운열\" \"최인호\" \"추경호\" \"추미애\" \"추혜선\" \"표창원\" \"하태경\" \"한선교\" \"한정애\" \"함진규\" \"홍문종\" \"홍문표\" \"홍영표\" \"홍의락\" \"홍익표\" \"홍일표\" \"홍철호\" \"황영철\" \"황주홍\" \"황희\")"}
;; <=

;; **
;;; #### 각 의원에 대한 Observe를 parameter를 뽑는 방식으로 만들 경우
;; **

;; @@
(defquery single [obs]
  (let [a (+ 1(sample (poisson 1)))
        b (+ 1(sample (poisson 1)))]
   (map (fn [x] (observe (beta a b) x)) obs)
 (sample (beta a b))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;pro1/single</span>","value":"#'pro1/single"}
;; <=

;; @@
; (key, idx): (pro, abs) -> 0.4, (cons, abs) -> 0.6 같은 식으로 주는 것도 의미가 있지 않을까?
; cosine similarity 를 먼저 구해서, 그 similarity가 낮은 의원들 위주로 plot을 해 보면 더 빨리 찾을 수 있을지도 모른다.
(defn test [idx]
  (let [ob   (map encode-row (filter
                    (fn [row] (not (or (= (nth row key-idx) "abs") (= (nth row idx) "abs"))))
                    (map rest (rest vote_result))))
        enc  (map (fn [y] (map (fn [x] (if x 0.9 0.01)) y)) ob) ; 이 값을 어떻게 주느냐에 따른 차이가 꽤 큰데..
        lazy (doquery :ipmcmc single [(map (fn [x] (nth x idx)) enc)])
        samp (map :result (take-nth 10 (take 10000 (drop 5000 lazy))))]
   (print (nth members key-idx) " " (nth members idx) ": " (count enc) "\n")
   (plot/histogram samp :normalize :probability :bins 90)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;pro1/test</span>","value":"#'pro1/test"}
;; <=

;; @@
(test (index-of "나경원" members))
;; @@
;; ->
;;; 902
;; <-
;; =>
;;; {"type":"vega","content":{"width":400,"height":247.2187957763672,"padding":{"top":10,"left":55,"bottom":40,"right":10},"data":[{"name":"8e63a99e-c23d-4fdd-8bf5-b77ad361c0e1","values":[{"x":0.008979097486843115,"y":0},{"x":0.01998902983584002,"y":0.002},{"x":0.03099896218483693,"y":0.006},{"x":0.042008894533833836,"y":0.0},{"x":0.053018826882830744,"y":0.01},{"x":0.06402875923182766,"y":0.002},{"x":0.07503869158082457,"y":0.006},{"x":0.08604862392982149,"y":0.006},{"x":0.0970585562788184,"y":0.004},{"x":0.10806848862781532,"y":0.002},{"x":0.11907842097681223,"y":0.006},{"x":0.13008835332580915,"y":0.004},{"x":0.14109828567480606,"y":0.002},{"x":0.15210821802380298,"y":0.006},{"x":0.1631181503727999,"y":0.008},{"x":0.1741280827217968,"y":0.002},{"x":0.18513801507079372,"y":0.002},{"x":0.19614794741979064,"y":0.004},{"x":0.20715787976878755,"y":0.006},{"x":0.21816781211778447,"y":0.01},{"x":0.22917774446678138,"y":0.0},{"x":0.2401876768157783,"y":0.008},{"x":0.2511976091647752,"y":0.002},{"x":0.26220754151377207,"y":0.004},{"x":0.27321747386276896,"y":0.01},{"x":0.28422740621176584,"y":0.016},{"x":0.29523733856076273,"y":0.006},{"x":0.3062472709097596,"y":0.002},{"x":0.3172572032587565,"y":0.004},{"x":0.3282671356077534,"y":0.022},{"x":0.3392770679567503,"y":0.01},{"x":0.35028700030574716,"y":0.008},{"x":0.36129693265474405,"y":0.008},{"x":0.37230686500374094,"y":0.01},{"x":0.3833167973527378,"y":0.006},{"x":0.3943267297017347,"y":0.006},{"x":0.4053366620507316,"y":0.018},{"x":0.4163465943997285,"y":0.006},{"x":0.4273565267487254,"y":0.006},{"x":0.43836645909772226,"y":0.008},{"x":0.44937639144671915,"y":0.006},{"x":0.46038632379571603,"y":0.014},{"x":0.4713962561447129,"y":0.012},{"x":0.4824061884937098,"y":0.014},{"x":0.4934161208427067,"y":0.016},{"x":0.5044260531917036,"y":0.016},{"x":0.5154359855407006,"y":0.004},{"x":0.5264459178896975,"y":0.008},{"x":0.5374558502386945,"y":0.018},{"x":0.5484657825876914,"y":0.004},{"x":0.5594757149366884,"y":0.01},{"x":0.5704856472856853,"y":0.008},{"x":0.5814955796346822,"y":0.01},{"x":0.5925055119836792,"y":0.008},{"x":0.6035154443326761,"y":0.016},{"x":0.6145253766816731,"y":0.02},{"x":0.62553530903067,"y":0.006},{"x":0.636545241379667,"y":0.018},{"x":0.6475551737286639,"y":0.02},{"x":0.6585651060776608,"y":0.01},{"x":0.6695750384266578,"y":0.006},{"x":0.6805849707756547,"y":0.014},{"x":0.6915949031246517,"y":0.018},{"x":0.7026048354736486,"y":0.018},{"x":0.7136147678226455,"y":0.014},{"x":0.7246247001716425,"y":0.006},{"x":0.7356346325206394,"y":0.01},{"x":0.7466445648696364,"y":0.008},{"x":0.7576544972186333,"y":0.026},{"x":0.7686644295676303,"y":0.014},{"x":0.7796743619166272,"y":0.02},{"x":0.7906842942656241,"y":0.006},{"x":0.8016942266146211,"y":0.008},{"x":0.812704158963618,"y":0.006},{"x":0.823714091312615,"y":0.02},{"x":0.8347240236616119,"y":0.012},{"x":0.8457339560106089,"y":0.006},{"x":0.8567438883596058,"y":0.024},{"x":0.8677538207086027,"y":0.014},{"x":0.8787637530575997,"y":0.016},{"x":0.8897736854065966,"y":0.02},{"x":0.9007836177555936,"y":0.016},{"x":0.9117935501045905,"y":0.024},{"x":0.9228034824535875,"y":0.018},{"x":0.9338134148025844,"y":0.018},{"x":0.9448233471515813,"y":0.014},{"x":0.9558332795005783,"y":0.028},{"x":0.9668432118495752,"y":0.024},{"x":0.9778531441985722,"y":0.04},{"x":0.9888630765475691,"y":0.016},{"x":0.999873008896566,"y":0.03},{"x":1.0108829412455629,"y":0}]}],"marks":[{"type":"line","from":{"data":"8e63a99e-c23d-4fdd-8bf5-b77ad361c0e1"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"interpolate":{"value":"step-before"},"fill":{"value":"steelblue"},"fillOpacity":{"value":0.4},"stroke":{"value":"steelblue"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}}],"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"8e63a99e-c23d-4fdd-8bf5-b77ad361c0e1","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":{"data":"8e63a99e-c23d-4fdd-8bf5-b77ad361c0e1","field":"data.y"}}],"axes":[{"type":"x","scale":"x"},{"type":"y","scale":"y"}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :data [{:name \"8e63a99e-c23d-4fdd-8bf5-b77ad361c0e1\", :values ({:x 0.008979097486843115, :y 0} {:x 0.01998902983584002, :y 0.002} {:x 0.03099896218483693, :y 0.006} {:x 0.042008894533833836, :y 0.0} {:x 0.053018826882830744, :y 0.01} {:x 0.06402875923182766, :y 0.002} {:x 0.07503869158082457, :y 0.006} {:x 0.08604862392982149, :y 0.006} {:x 0.0970585562788184, :y 0.004} {:x 0.10806848862781532, :y 0.002} {:x 0.11907842097681223, :y 0.006} {:x 0.13008835332580915, :y 0.004} {:x 0.14109828567480606, :y 0.002} {:x 0.15210821802380298, :y 0.006} {:x 0.1631181503727999, :y 0.008} {:x 0.1741280827217968, :y 0.002} {:x 0.18513801507079372, :y 0.002} {:x 0.19614794741979064, :y 0.004} {:x 0.20715787976878755, :y 0.006} {:x 0.21816781211778447, :y 0.01} {:x 0.22917774446678138, :y 0.0} {:x 0.2401876768157783, :y 0.008} {:x 0.2511976091647752, :y 0.002} {:x 0.26220754151377207, :y 0.004} {:x 0.27321747386276896, :y 0.01} {:x 0.28422740621176584, :y 0.016} {:x 0.29523733856076273, :y 0.006} {:x 0.3062472709097596, :y 0.002} {:x 0.3172572032587565, :y 0.004} {:x 0.3282671356077534, :y 0.022} {:x 0.3392770679567503, :y 0.01} {:x 0.35028700030574716, :y 0.008} {:x 0.36129693265474405, :y 0.008} {:x 0.37230686500374094, :y 0.01} {:x 0.3833167973527378, :y 0.006} {:x 0.3943267297017347, :y 0.006} {:x 0.4053366620507316, :y 0.018} {:x 0.4163465943997285, :y 0.006} {:x 0.4273565267487254, :y 0.006} {:x 0.43836645909772226, :y 0.008} {:x 0.44937639144671915, :y 0.006} {:x 0.46038632379571603, :y 0.014} {:x 0.4713962561447129, :y 0.012} {:x 0.4824061884937098, :y 0.014} {:x 0.4934161208427067, :y 0.016} {:x 0.5044260531917036, :y 0.016} {:x 0.5154359855407006, :y 0.004} {:x 0.5264459178896975, :y 0.008} {:x 0.5374558502386945, :y 0.018} {:x 0.5484657825876914, :y 0.004} {:x 0.5594757149366884, :y 0.01} {:x 0.5704856472856853, :y 0.008} {:x 0.5814955796346822, :y 0.01} {:x 0.5925055119836792, :y 0.008} {:x 0.6035154443326761, :y 0.016} {:x 0.6145253766816731, :y 0.02} {:x 0.62553530903067, :y 0.006} {:x 0.636545241379667, :y 0.018} {:x 0.6475551737286639, :y 0.02} {:x 0.6585651060776608, :y 0.01} {:x 0.6695750384266578, :y 0.006} {:x 0.6805849707756547, :y 0.014} {:x 0.6915949031246517, :y 0.018} {:x 0.7026048354736486, :y 0.018} {:x 0.7136147678226455, :y 0.014} {:x 0.7246247001716425, :y 0.006} {:x 0.7356346325206394, :y 0.01} {:x 0.7466445648696364, :y 0.008} {:x 0.7576544972186333, :y 0.026} {:x 0.7686644295676303, :y 0.014} {:x 0.7796743619166272, :y 0.02} {:x 0.7906842942656241, :y 0.006} {:x 0.8016942266146211, :y 0.008} {:x 0.812704158963618, :y 0.006} {:x 0.823714091312615, :y 0.02} {:x 0.8347240236616119, :y 0.012} {:x 0.8457339560106089, :y 0.006} {:x 0.8567438883596058, :y 0.024} {:x 0.8677538207086027, :y 0.014} {:x 0.8787637530575997, :y 0.016} {:x 0.8897736854065966, :y 0.02} {:x 0.9007836177555936, :y 0.016} {:x 0.9117935501045905, :y 0.024} {:x 0.9228034824535875, :y 0.018} {:x 0.9338134148025844, :y 0.018} {:x 0.9448233471515813, :y 0.014} {:x 0.9558332795005783, :y 0.028} {:x 0.9668432118495752, :y 0.024} {:x 0.9778531441985722, :y 0.04} {:x 0.9888630765475691, :y 0.016} {:x 0.999873008896566, :y 0.03} {:x 1.0108829412455629, :y 0})}], :marks [{:type \"line\", :from {:data \"8e63a99e-c23d-4fdd-8bf5-b77ad361c0e1\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :interpolate {:value \"step-before\"}, :fill {:value \"steelblue\"}, :fillOpacity {:value 0.4}, :stroke {:value \"steelblue\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"8e63a99e-c23d-4fdd-8bf5-b77ad361c0e1\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"8e63a99e-c23d-4fdd-8bf5-b77ad361c0e1\", :field \"data.y\"}}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}]}}"}
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

;; @@
(def key-idx (index-of "홍문종" members))
(test (index-of "김용태" members))
;; @@
;; ->
;;; 홍문종   김용태 :  74 
;;; 
;; <-
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;pro1/key-idx</span>","value":"#'pro1/key-idx"},{"type":"vega","content":{"width":400,"height":247.2187957763672,"padding":{"top":10,"left":55,"bottom":40,"right":10},"data":[{"name":"5d9459a6-dcd9-4c9e-a08b-49c34adf8f65","values":[{"x":0.0027376154538525377,"y":0},{"x":0.013813034825072752,"y":0.011},{"x":0.024888454196292965,"y":0.019},{"x":0.03596387356751318,"y":0.015},{"x":0.04703929293873339,"y":0.009},{"x":0.0581147123099536,"y":0.014},{"x":0.06919013168117381,"y":0.01},{"x":0.08026555105239402,"y":0.014},{"x":0.09134097042361423,"y":0.015},{"x":0.10241638979483444,"y":0.017},{"x":0.11349180916605466,"y":0.008},{"x":0.12456722853727487,"y":0.012},{"x":0.1356426479084951,"y":0.013},{"x":0.14671806727971531,"y":0.013},{"x":0.15779348665093554,"y":0.01},{"x":0.16886890602215576,"y":0.01},{"x":0.179944325393376,"y":0.01},{"x":0.1910197447645962,"y":0.011},{"x":0.20209516413581644,"y":0.015},{"x":0.21317058350703666,"y":0.014},{"x":0.22424600287825688,"y":0.007},{"x":0.2353214222494771,"y":0.012},{"x":0.24639684162069733,"y":0.014},{"x":0.25747226099191756,"y":0.009},{"x":0.26854768036313775,"y":0.016},{"x":0.27962309973435795,"y":0.009},{"x":0.29069851910557815,"y":0.016},{"x":0.30177393847679834,"y":0.015},{"x":0.31284935784801854,"y":0.007},{"x":0.32392477721923874,"y":0.017},{"x":0.33500019659045893,"y":0.008},{"x":0.34607561596167913,"y":0.01},{"x":0.3571510353328993,"y":0.011},{"x":0.3682264547041195,"y":0.007},{"x":0.3793018740753397,"y":0.009},{"x":0.3903772934465599,"y":0.008},{"x":0.4014527128177801,"y":0.014},{"x":0.4125281321890003,"y":0.011},{"x":0.4236035515602205,"y":0.013},{"x":0.4346789709314407,"y":0.012},{"x":0.4457543903026609,"y":0.008},{"x":0.4568298096738811,"y":0.009},{"x":0.4679052290451013,"y":0.014},{"x":0.4789806484163215,"y":0.007},{"x":0.4900560677875417,"y":0.015},{"x":0.5011314871587619,"y":0.014},{"x":0.5122069065299821,"y":0.014},{"x":0.5232823259012024,"y":0.011},{"x":0.5343577452724226,"y":0.011},{"x":0.5454331646436429,"y":0.011},{"x":0.5565085840148631,"y":0.013},{"x":0.5675840033860834,"y":0.015},{"x":0.5786594227573036,"y":0.011},{"x":0.5897348421285239,"y":0.006},{"x":0.6008102614997441,"y":0.012},{"x":0.6118856808709644,"y":0.009},{"x":0.6229611002421847,"y":0.014},{"x":0.6340365196134049,"y":0.016},{"x":0.6451119389846252,"y":0.011},{"x":0.6561873583558454,"y":0.01},{"x":0.6672627777270657,"y":0.009},{"x":0.6783381970982859,"y":0.014},{"x":0.6894136164695062,"y":0.008},{"x":0.7004890358407264,"y":0.011},{"x":0.7115644552119467,"y":0.007},{"x":0.7226398745831669,"y":0.011},{"x":0.7337152939543872,"y":0.007},{"x":0.7447907133256074,"y":0.014},{"x":0.7558661326968277,"y":0.019},{"x":0.7669415520680479,"y":0.008},{"x":0.7780169714392682,"y":0.008},{"x":0.7890923908104884,"y":0.009},{"x":0.8001678101817087,"y":0.005},{"x":0.8112432295529289,"y":0.012},{"x":0.8223186489241492,"y":0.011},{"x":0.8333940682953694,"y":0.009},{"x":0.8444694876665897,"y":0.009},{"x":0.8555449070378099,"y":0.005},{"x":0.8666203264090302,"y":0.018},{"x":0.8776957457802504,"y":0.009},{"x":0.8887711651514707,"y":0.003},{"x":0.899846584522691,"y":0.014},{"x":0.9109220038939112,"y":0.01},{"x":0.9219974232651315,"y":0.009},{"x":0.9330728426363517,"y":0.008},{"x":0.944148262007572,"y":0.007},{"x":0.9552236813787922,"y":0.009},{"x":0.9662991007500125,"y":0.011},{"x":0.9773745201212327,"y":0.007},{"x":0.988449939492453,"y":0.012},{"x":0.9995253588636732,"y":0.011},{"x":1.0106007782348934,"y":0}]}],"marks":[{"type":"line","from":{"data":"5d9459a6-dcd9-4c9e-a08b-49c34adf8f65"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"interpolate":{"value":"step-before"},"fill":{"value":"steelblue"},"fillOpacity":{"value":0.4},"stroke":{"value":"steelblue"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}}],"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"5d9459a6-dcd9-4c9e-a08b-49c34adf8f65","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":{"data":"5d9459a6-dcd9-4c9e-a08b-49c34adf8f65","field":"data.y"}}],"axes":[{"type":"x","scale":"x"},{"type":"y","scale":"y"}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :data [{:name \"5d9459a6-dcd9-4c9e-a08b-49c34adf8f65\", :values ({:x 0.0027376154538525377, :y 0} {:x 0.013813034825072752, :y 0.011} {:x 0.024888454196292965, :y 0.019} {:x 0.03596387356751318, :y 0.015} {:x 0.04703929293873339, :y 0.009} {:x 0.0581147123099536, :y 0.014} {:x 0.06919013168117381, :y 0.01} {:x 0.08026555105239402, :y 0.014} {:x 0.09134097042361423, :y 0.015} {:x 0.10241638979483444, :y 0.017} {:x 0.11349180916605466, :y 0.008} {:x 0.12456722853727487, :y 0.012} {:x 0.1356426479084951, :y 0.013} {:x 0.14671806727971531, :y 0.013} {:x 0.15779348665093554, :y 0.01} {:x 0.16886890602215576, :y 0.01} {:x 0.179944325393376, :y 0.01} {:x 0.1910197447645962, :y 0.011} {:x 0.20209516413581644, :y 0.015} {:x 0.21317058350703666, :y 0.014} {:x 0.22424600287825688, :y 0.007} {:x 0.2353214222494771, :y 0.012} {:x 0.24639684162069733, :y 0.014} {:x 0.25747226099191756, :y 0.009} {:x 0.26854768036313775, :y 0.016} {:x 0.27962309973435795, :y 0.009} {:x 0.29069851910557815, :y 0.016} {:x 0.30177393847679834, :y 0.015} {:x 0.31284935784801854, :y 0.007} {:x 0.32392477721923874, :y 0.017} {:x 0.33500019659045893, :y 0.008} {:x 0.34607561596167913, :y 0.01} {:x 0.3571510353328993, :y 0.011} {:x 0.3682264547041195, :y 0.007} {:x 0.3793018740753397, :y 0.009} {:x 0.3903772934465599, :y 0.008} {:x 0.4014527128177801, :y 0.014} {:x 0.4125281321890003, :y 0.011} {:x 0.4236035515602205, :y 0.013} {:x 0.4346789709314407, :y 0.012} {:x 0.4457543903026609, :y 0.008} {:x 0.4568298096738811, :y 0.009} {:x 0.4679052290451013, :y 0.014} {:x 0.4789806484163215, :y 0.007} {:x 0.4900560677875417, :y 0.015} {:x 0.5011314871587619, :y 0.014} {:x 0.5122069065299821, :y 0.014} {:x 0.5232823259012024, :y 0.011} {:x 0.5343577452724226, :y 0.011} {:x 0.5454331646436429, :y 0.011} {:x 0.5565085840148631, :y 0.013} {:x 0.5675840033860834, :y 0.015} {:x 0.5786594227573036, :y 0.011} {:x 0.5897348421285239, :y 0.006} {:x 0.6008102614997441, :y 0.012} {:x 0.6118856808709644, :y 0.009} {:x 0.6229611002421847, :y 0.014} {:x 0.6340365196134049, :y 0.016} {:x 0.6451119389846252, :y 0.011} {:x 0.6561873583558454, :y 0.01} {:x 0.6672627777270657, :y 0.009} {:x 0.6783381970982859, :y 0.014} {:x 0.6894136164695062, :y 0.008} {:x 0.7004890358407264, :y 0.011} {:x 0.7115644552119467, :y 0.007} {:x 0.7226398745831669, :y 0.011} {:x 0.7337152939543872, :y 0.007} {:x 0.7447907133256074, :y 0.014} {:x 0.7558661326968277, :y 0.019} {:x 0.7669415520680479, :y 0.008} {:x 0.7780169714392682, :y 0.008} {:x 0.7890923908104884, :y 0.009} {:x 0.8001678101817087, :y 0.005} {:x 0.8112432295529289, :y 0.012} {:x 0.8223186489241492, :y 0.011} {:x 0.8333940682953694, :y 0.009} {:x 0.8444694876665897, :y 0.009} {:x 0.8555449070378099, :y 0.005} {:x 0.8666203264090302, :y 0.018} {:x 0.8776957457802504, :y 0.009} {:x 0.8887711651514707, :y 0.003} {:x 0.899846584522691, :y 0.014} {:x 0.9109220038939112, :y 0.01} {:x 0.9219974232651315, :y 0.009} {:x 0.9330728426363517, :y 0.008} {:x 0.944148262007572, :y 0.007} {:x 0.9552236813787922, :y 0.009} {:x 0.9662991007500125, :y 0.011} {:x 0.9773745201212327, :y 0.007} {:x 0.988449939492453, :y 0.012} {:x 0.9995253588636732, :y 0.011} {:x 1.0106007782348934, :y 0})}], :marks [{:type \"line\", :from {:data \"5d9459a6-dcd9-4c9e-a08b-49c34adf8f65\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :interpolate {:value \"step-before\"}, :fill {:value \"steelblue\"}, :fillOpacity {:value 0.4}, :stroke {:value \"steelblue\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"5d9459a6-dcd9-4c9e-a08b-49c34adf8f65\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"5d9459a6-dcd9-4c9e-a08b-49c34adf8f65\", :field \"data.y\"}}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}]}}"}],"value":"[#'pro1/key-idx,#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :data [{:name \"5d9459a6-dcd9-4c9e-a08b-49c34adf8f65\", :values ({:x 0.0027376154538525377, :y 0} {:x 0.013813034825072752, :y 0.011} {:x 0.024888454196292965, :y 0.019} {:x 0.03596387356751318, :y 0.015} {:x 0.04703929293873339, :y 0.009} {:x 0.0581147123099536, :y 0.014} {:x 0.06919013168117381, :y 0.01} {:x 0.08026555105239402, :y 0.014} {:x 0.09134097042361423, :y 0.015} {:x 0.10241638979483444, :y 0.017} {:x 0.11349180916605466, :y 0.008} {:x 0.12456722853727487, :y 0.012} {:x 0.1356426479084951, :y 0.013} {:x 0.14671806727971531, :y 0.013} {:x 0.15779348665093554, :y 0.01} {:x 0.16886890602215576, :y 0.01} {:x 0.179944325393376, :y 0.01} {:x 0.1910197447645962, :y 0.011} {:x 0.20209516413581644, :y 0.015} {:x 0.21317058350703666, :y 0.014} {:x 0.22424600287825688, :y 0.007} {:x 0.2353214222494771, :y 0.012} {:x 0.24639684162069733, :y 0.014} {:x 0.25747226099191756, :y 0.009} {:x 0.26854768036313775, :y 0.016} {:x 0.27962309973435795, :y 0.009} {:x 0.29069851910557815, :y 0.016} {:x 0.30177393847679834, :y 0.015} {:x 0.31284935784801854, :y 0.007} {:x 0.32392477721923874, :y 0.017} {:x 0.33500019659045893, :y 0.008} {:x 0.34607561596167913, :y 0.01} {:x 0.3571510353328993, :y 0.011} {:x 0.3682264547041195, :y 0.007} {:x 0.3793018740753397, :y 0.009} {:x 0.3903772934465599, :y 0.008} {:x 0.4014527128177801, :y 0.014} {:x 0.4125281321890003, :y 0.011} {:x 0.4236035515602205, :y 0.013} {:x 0.4346789709314407, :y 0.012} {:x 0.4457543903026609, :y 0.008} {:x 0.4568298096738811, :y 0.009} {:x 0.4679052290451013, :y 0.014} {:x 0.4789806484163215, :y 0.007} {:x 0.4900560677875417, :y 0.015} {:x 0.5011314871587619, :y 0.014} {:x 0.5122069065299821, :y 0.014} {:x 0.5232823259012024, :y 0.011} {:x 0.5343577452724226, :y 0.011} {:x 0.5454331646436429, :y 0.011} {:x 0.5565085840148631, :y 0.013} {:x 0.5675840033860834, :y 0.015} {:x 0.5786594227573036, :y 0.011} {:x 0.5897348421285239, :y 0.006} {:x 0.6008102614997441, :y 0.012} {:x 0.6118856808709644, :y 0.009} {:x 0.6229611002421847, :y 0.014} {:x 0.6340365196134049, :y 0.016} {:x 0.6451119389846252, :y 0.011} {:x 0.6561873583558454, :y 0.01} {:x 0.6672627777270657, :y 0.009} {:x 0.6783381970982859, :y 0.014} {:x 0.6894136164695062, :y 0.008} {:x 0.7004890358407264, :y 0.011} {:x 0.7115644552119467, :y 0.007} {:x 0.7226398745831669, :y 0.011} {:x 0.7337152939543872, :y 0.007} {:x 0.7447907133256074, :y 0.014} {:x 0.7558661326968277, :y 0.019} {:x 0.7669415520680479, :y 0.008} {:x 0.7780169714392682, :y 0.008} {:x 0.7890923908104884, :y 0.009} {:x 0.8001678101817087, :y 0.005} {:x 0.8112432295529289, :y 0.012} {:x 0.8223186489241492, :y 0.011} {:x 0.8333940682953694, :y 0.009} {:x 0.8444694876665897, :y 0.009} {:x 0.8555449070378099, :y 0.005} {:x 0.8666203264090302, :y 0.018} {:x 0.8776957457802504, :y 0.009} {:x 0.8887711651514707, :y 0.003} {:x 0.899846584522691, :y 0.014} {:x 0.9109220038939112, :y 0.01} {:x 0.9219974232651315, :y 0.009} {:x 0.9330728426363517, :y 0.008} {:x 0.944148262007572, :y 0.007} {:x 0.9552236813787922, :y 0.009} {:x 0.9662991007500125, :y 0.011} {:x 0.9773745201212327, :y 0.007} {:x 0.988449939492453, :y 0.012} {:x 0.9995253588636732, :y 0.011} {:x 1.0106007782348934, :y 0})}], :marks [{:type \"line\", :from {:data \"5d9459a6-dcd9-4c9e-a08b-49c34adf8f65\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :interpolate {:value \"step-before\"}, :fill {:value \"steelblue\"}, :fillOpacity {:value 0.4}, :stroke {:value \"steelblue\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"5d9459a6-dcd9-4c9e-a08b-49c34adf8f65\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"5d9459a6-dcd9-4c9e-a08b-49c34adf8f65\", :field \"data.y\"}}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}]}}]"}
;; <=

;; @@

;; @@
