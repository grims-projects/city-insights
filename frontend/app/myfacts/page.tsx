"use client";

import { useEffect, useState } from "react";
import Navbar from "../components/navbar/Navbar";
import { garamond } from "../layout";
import InsightCard from "../components/insightCard/InsightCard";
import axios from "axios";

type CityInsight = {
  id: string;
  cityName: string;
  cityDescription: string;
  insightText: string;
  imageUrl: string;
  wikiUrl: string;
};

export default function MyFacts() {
  const [cityInsights, setCityInsights] = useState<CityInsight[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      await axios.get<CityInsight[]>("http://localhost:8080/api/insight/city")
        .then(response => setCityInsights(response.data))
        .catch(error => console.error("Error fetching data:", error));
    };
    fetchData();
  }, []);

  const handleDelete = async (id: string) => {
    await axios.delete(`http://localhost:8080/api/insight/city/${id}`)
      .then(() => setCityInsights(cityInsights.filter(insight => insight.id !== id)))
      .catch(error => console.error("Error deleting insight:", error));
  };

  return (
    <main className={`min-h-screen ${garamond.className} flex items-center justify-center`}>
      <div className="grid gap-4 grid-cols-1 lg:grid-cols-2 2xl:grid-cols-3">
        {cityInsights.map((insight) => (
          <InsightCard key={insight.id} id={insight.id} cityName={insight.cityName} imageUrl={insight.imageUrl} insightText={insight.insightText} onDelete={() => handleDelete(insight.id)} />
        ))}
      </div>
      <div className="fixed bottom-0 w-full z-10">
        <Navbar setIsButtonClicked={Function} />
      </div>
    </main>
  )
}

