(ns tangarts.jcip
  (:require
   [clj-http.client :as client]
   [clojure.java.io :refer [writer file]]
   [clojure.string :as str]
   [pl.danieljanus.tagsoup :as soup])
  (:gen-class))

;; Downloader to get jcip source

(def +base-url+ "https://jcip.net/listings.html")
(def +root-url+ (-> +base-url+ (str/split #"listing") first))

(def web-page (soup/parse +base-url+))

(defn parse-div [coll]
  (condp = (first coll)
    :h2 (get coll 2)
    :ul (distinct (filter (comp not nil?)
                     (map #(when (and (= (get-in % [3 0]) :a)
                                      (= \l (first (get-in % [3 1 :href]))))
                             (get-in % [3 1 :href]))  coll)))
    nil))

(def div (-> web-page
             (get-in [3 3])
             (subvec 5)))

(def folder-files (->> div
                       (map parse-div)
                       (partition 2 2)))

(defn write-file 
  [content file append?]
  (with-open [w (writer file :append append?)]
    (.write w content)))

(defn write-java 
  [file folder]
  (let [filename (get (str/split file #"/") 1)]
    (-> +root-url+
        (str ,,, "listings/" filename)
        slurp
        (write-file ,,, (str "resources/" folder "/" filename) false))))

(defn do-download-all 
  []
  (doseq [[folder files] folder-files]
    (println folder)
    (.mkdir (file "resources/" folder))
    (try
      (doseq [file files]
        (write-java file folder)
        (println file "   => written to " (str "resources/" folder "/" file)))
      (catch Exception e
        (println (str "caught exception: " (.getMessage e)))))))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (do-download-all))
