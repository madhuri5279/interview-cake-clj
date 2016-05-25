(ns rectangular-love.core
  (:gen-class))

(defn- overlapping-range [range-a range-b]
  (let [[under over] (sort-by :start [range-a range-b])]
    (when (>= (under :end) (over :start))
      {:start (max (under :start) (over :start))
       :end (min (under :end) (over :end))})))

(defn- rect->vertical-range [rect]
  {:start (rect :y) :end (+ (rect :y) (rect :height))})

(defn- rect->horizontal-range [rect]
  {:start (rect :x) :end (+ (rect :x) (rect :width))})

(defn- find-vertical-overlap [rect-a rect-b]
  (overlapping-range (rect->vertical-range rect-a)
                     (rect->vertical-range rect-b)))

(defn- find-horizontal-overlap [rect-a rect-b]
  (overlapping-range (rect->horizontal-range rect-a)
                     (rect->horizontal-range rect-b)))

(defn- length [{:keys [start end]}]
  (- end start))

(defn rectangular-overlap
  "O(1) time solution - will return overlapping rectangle, nil if no overlap."
  [rect-a rect-b]
  (let [vertical-overlapping-range (find-vertical-overlap rect-a rect-b)
        horizontal-overlapping-range (find-horizontal-overlap rect-a rect-b)]
    (when (and vertical-overlapping-range horizontal-overlapping-range)
      {:x (horizontal-overlapping-range :start)
       :y (vertical-overlapping-range :start)
       :width (length horizontal-overlapping-range)
       :height (length vertical-overlapping-range)})))
