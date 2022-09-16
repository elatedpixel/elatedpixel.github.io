(ns app.hello
  (:require [reagent.core :as r]))

(defn click-counter [click-count]
  [:div.box
   "The atom " [:code "click-count"] " has value: "
   @click-count ". "
   [:input {:type "button"
            :value "Click me!"
            :class "button is-primary"
            :on-click #(swap! click-count inc)}]])

(def click-count (r/atom 0))

(defn upload-form [form-data]
  (let [form-state (r/atom {:is-loading false})]
    (fn []
      [:div.box
       [:div.field
        [:label.label "First Name"]
        [:div.control
         [:input.input {:placeholder "Tell us your first (preferred) name!"
                        :on-change   #(swap! form-data assoc :first-name (-> % .-target .-value))
                        :value       (:first-name @form-data)
                        :type        "text"}]]]
       [:div.field
        [:label.label "Last Name"]
        [:div.control
         [:input.input {:placeholder "What is your last name?"
                        :on-change   #(swap! form-data assoc :last-name (-> % .-target .-value))
                        :value       (:last-name @form-data)
                        :type        "text"}]]]
       [:div.field.is-grouped.is-grouped-right
        [:p.control
         (let [is-loading (:is-loading @form-state)]
           [:a.button.is-link {:class (cond
                                        is-loading "is-loading")
                               :disabled is-loading
                               :on-click #(do
                                            (swap! form-state assoc :is-loading true)
                                            (js/setTimeout (fn [] (swap! form-state assoc :is-loading false)) 2000))}
            "Upload"])
         [:a.button.is-light "Cancel"]]]
       [:pre (js/JSON.stringify (clj->js @form-data) nil 2)]])))

(defn hello []
  [:<>
   [:nav.navbar]
   [:section.section
    [:h1.title "Elatedpixel Dojo"]
    [:h3.subtitle "pixels are more productive when they're happy"]
    [:p "Hello, dojo is running!"]
    [:p "Here's an example of using a component with state:"]
    [click-counter click-count]]
   [:section.section
    [:span.is-size-2 "Upload form"]
    [upload-form (r/atom {:first-name nil
                          :last-name nil})]]])
