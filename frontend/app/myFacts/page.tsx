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

  const MyFacts: React.FC = () => {
    const [cityInsights, setCityInsights] = useState<CityInsight[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get<CityInsight[]>("http://localhost:8080/api/insight/city");
        setCityInsights(response.data);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, []);
  return (
        <main className={`flex min-h-screen flex-col items-center justify-between ${garamond.className} pl-16 pr-16 pt-6 pb-0`}>
          {cityInsights.map((insight) => (
            <InsightCard key={insight.id} cityName={insight.cityName} imageUrl={insight.imageUrl} insightText={insight.insightText} />
          ))}
          <Navbar setIsButtonClicked={Function}/>
        </main>
    );
}
export default MyFacts;
