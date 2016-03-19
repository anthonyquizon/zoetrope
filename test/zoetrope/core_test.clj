(ns zoetrope.core-test
  (:require [clojure.test :refer :all]
            [zoetrope.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))


;; TODO output/dom -> vnode
;; TODO property based testing
;; [:div]
;; [:div {...}]
;; [:div [:div ...]]
;; [:div String|Int]
;; [:div [:div ...] [:div ...]]
;; [:div {...} String|Int]]
;; [:div {...} [:div ...] [:div ...]]

