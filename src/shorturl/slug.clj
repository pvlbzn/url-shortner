(ns shorturl.slug)


(def charset "ABCDEFGHIJKLMNOPQRSTUVXYZ")
; 8 characters long provides 26^8 unique combination
(def default-length 8)

; create random charset of length n
(defn rnd1
      [len]
  (loop [s "", x len]
    (if (pos? x)
      (recur (str s (rand-nth charset)) (dec x))
    s)))

(defn to-string
  [lseq]
  ; Apply here takes a list of arguments and applies them to a function
  ; individually. Those two are equivalent:
  ; (apply str [\A \B \C])
  ; (str \A \B \C)
  (apply str lseq))

(defn rnd2
  [len]
  (to-string (take len (repeatedly #(rand-nth charset)))))

(defn rnd3
  [len]
  (-> (take len (repeatedly #(rand-nth charset)))
      (to-string)))

(defn rnd4
  [len]
  (let [s (take len (repeatedly #(rand-nth charset)))]
   (to-string s)))

(comment
  (rand-nth charset)
  ; Alternatively:
  ; Where `repeatedly` is a generator which will execute function repeatedly.
  ; #(...) is a short form of (fn [] (...))
  (take 8 (repeatedly #(rand-nth charset)))
  (to-string (take 8 (repeatedly (fn [] (rand-nth charset)))))
  (rnd1 default-length)
  (rnd2 default-length)
  (rnd3 default-length)
  (rnd4 default-length)
  )
