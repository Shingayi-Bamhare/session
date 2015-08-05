(ns session.loop
  (:require
    [om.core :as om]
    [om.dom :as dom]
    [session.editor :as editor]))


(defn create-output [data owner]
  (reify om/IRender
    (render [_]
      (dom/div nil (:text data)))))

(defn create-loop [data owner]
  (reify om/IRender
    (render [_]
      (dom/div
        nil
        (om/build
          editor/create-editor
          (:input data)
          {:opts
           {:id (:id data)
            :loop-output-fn
                (fn [x]
                  ;(println "handler output")
                  (om/transact!
                    (om/get-props owner)
                    [:output :text]
                    (fn [e] x)))}})
        (om/build create-output (:output data))))))