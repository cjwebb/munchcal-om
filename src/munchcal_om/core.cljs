(ns ^:figwheel-always munchcal-om.core
    (:require [om.core :as om :include-macros true]
              [om.dom :as dom :include-macros true]))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state 
  (atom 
    {:text "Hello world!"
     :nav-links [{:name "Calendar" :href "/calendar"}
                 {:name "Recipes" :href "/recipes"}]}))

(defn nav-link-view [data owner]
  "Render one link in the navbar"
  (reify
    om/IRender
    (render [_]
      (dom/li nil 
        (dom/a #js {:href (:href data)} (:name data))))))

(defn header-view [data owner]
  "Render page header. Mainly the navbar"
  (reify
    om/IRender
    (render [_]
      (dom/nav nil
        (dom/div #js {:className "container"}
          (dom/div #js {:className "navbar-header"})
          (dom/div #js {:id "navbar" :className "collapse navbar-collapse"})
            (apply dom/ul #js {:className "nav navbar-nav"}
              (om/build-all nav-link-view (:nav-links data)))
                 )))))

(om/root
  (fn [data owner]
    (reify
      om/IRender
      (render [_]
        (dom/div #js {:className "container"}
          (dom/p nil (:text data))))))
  app-state
  {:target (. js/document (getElementById "app"))})

(om/root header-view app-state
  {:target (. js/document (getElementById "header-view"))})

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

