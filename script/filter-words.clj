#!/usr/bin/env bb

(defn- log2 [^Double n]
  (/ (Math/log n) (Math/log 2)))

(defn- information-gain [words word]
  (let [prior (log2 (count words))
        gain (->> words
                  (filter #(not-empty (set/intersection (set word) (set %))))
                  count)]
    (- prior (log2 gain))))

(defn entropy [words word]
  (let [gain (information-gain words word)]
    (Math/pow 2 gain)))

(defn entropy-threshold [words n]
  (fn [word] (< n (entropy words word))))

(defn run [args]
  (let [[i o & _] args
        words     (str/split-lines (slurp i))]
    (map (juxt identity (partial entropy words))
         words)))

(run ["words.txt" "out.txt"])
