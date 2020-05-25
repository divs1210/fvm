(ns fvm.ops-test
  (:require [clojure.test :refer :all]
            [fvm.core :as fvm]))

(defn apply-op [op stack-in]
  (:stack (fvm/interpret {:code [{:op op}]
                          :stack stack-in})))

(deftest math-test
  (let [stack-in [2 3]]
    (is (= [5] (apply-op :add stack-in)))
    (is (= [-1] (apply-op :sub stack-in)))
    (is (= [6] (apply-op :mul stack-in)))
    (is (= [2/3] (apply-op :div stack-in)))))

(deftest logic-test
  (let [eq-insn {:op :eq?
                 :then [{:op :pop}
                        {:op :pop}
                        {:op :push
                         :value true}]
                 :else [{:op :pop}
                        {:op :pop}
                        {:op :push
                         :value false}]}]
    (is (= {:code []
            :stack [true]
            :ops {}}
           (fvm/interpret {:code [eq-insn]
                           :stack [:a :a]})))
    (is (= {:code []
            :stack [false]
            :ops {}}
           (fvm/interpret {:code [eq-insn]
                           :stack [:a :b]})))))

(deftest stack-test
  (is (= [:a 1 2]
         (:stack (fvm/interpret {:code [{:op :push
                                         :value :a}]
                                 :stack [1 2]}))))
  (is (= [2] (apply-op :pop [1 2])))
  (is (= [:a :a] (apply-op :dup [:a])))
  (is (= [2 1] (apply-op :swap [1 2]))))

(deftest defop-test
  (is (= {:code []
          :stack nil
          :ops {:add-2 {:body [{:op :push
                                :value 2}
                               {:op :add}]}}}
         (fvm/interpret {:code [{:op :defop
                                 :name :add-2
                                 :value [{:op :push
                                          :value 2}
                                         {:op :add}]}]}))))

(deftest print-test
  (is (= "hi"
         (with-out-str
           (apply-op :print ["hi"])))))
