(ns fvm.std)

(def std
  [{:op :defop
    :name :dec
    :value [{:op :push
             :value 1}
            {:op :swap}
            {:op :sub}]}

   {:op :defop
    :name :neg
    :value [{:op :push
             :value 0}
            {:op :sub}]}

   {:op :defop
    :name :println
    :value [{:op :print}
            {:op :push
             :value "\n"}
            {:op :print}]}])
