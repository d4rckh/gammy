import React from "react";
import { useStatAnalytics } from "@/lib/api/queries";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
  PieChart,
  Pie,
  Cell,
  Legend,
  ResponsiveContainer,
} from "recharts";
import { ChartContainer, ChartTooltip, ChartTooltipContent } from "../ui/chart";

interface StatsAnalyticsProps {
  apiName: string;
}

export default function StatsAnalytics({ apiName }: StatsAnalyticsProps) {
  const { data: analytics, isLoading, isError } = useStatAnalytics(apiName);

  if (isLoading) {
    return <div>Loading statistics...</div>;
  }
  if (isError || !analytics) {
    return <div>Failed to load statistics.</div>;
  }

  const {
    totalNonDefaultEntries,
    averageValue,
    standardDeviation,
    distributionOfValues,
    mostCommonValue,
    percentAboveAverage,
  } = analytics;

  // Prepare data for charts
  const barData = Object.entries(distributionOfValues).map(([value, count]) => ({
    name: value,
    count,
  }));

  const pieData = [
    { name: "Above", value: percentAboveAverage },
    { name: "Below or Equal", value: 100 - percentAboveAverage },
  ];

  const COLORS = ["#4f46e5", "#e0e7ff"];

  return (
    <div className="space-y-6">
      {/* Summary cards */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-4">
        <Card>
          <CardHeader>
            <CardTitle>Total Non-Default Entries</CardTitle>
          </CardHeader>
          <CardContent>
            <span className="text-2xl font-semibold">{totalNonDefaultEntries}</span>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Average Value</CardTitle>
          </CardHeader>
          <CardContent>
            <span className="text-2xl font-semibold">{averageValue.toFixed(2)}</span>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Standard Deviation</CardTitle>
          </CardHeader>
          <CardContent>
            <span className="text-2xl font-semibold">{standardDeviation.toFixed(2)}</span>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Most Common Value</CardTitle>
          </CardHeader>
          <CardContent>
            <span className="text-2xl font-semibold">{mostCommonValue?.toFixed(1) ?? "N/A"}</span>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Percent Above Average</CardTitle>
          </CardHeader>
          <CardContent>
            <span className="text-2xl font-semibold">{percentAboveAverage.toFixed(1)}%</span>
          </CardContent>
        </Card>
      </div>

      {/* Charts */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <Card>
          <CardHeader>
            <CardTitle>Value Distribution</CardTitle>
          </CardHeader>
          <CardContent className="h-64">
            <ChartContainer config={{}}>
              <ResponsiveContainer width="100%" height="100%">
                <BarChart data={barData} margin={{ top: 20, right: 30, left: 0, bottom: 5 }}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="name" />
                  <YAxis />
                  <ChartTooltip content={<ChartTooltipContent />} />
                  <Bar dataKey="count" fill="#4f46e5" />
                </BarChart>
              </ResponsiveContainer>
            </ChartContainer>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Above Average Breakdown</CardTitle>
          </CardHeader>
          <CardContent className="h-64 flex justify-center items-center">
            <ResponsiveContainer width="100%" height="100%">
              <PieChart>
                <Pie
                  data={pieData}
                  dataKey="value"
                  nameKey="name"
                  outerRadius={80}
                  fill="#8884d8"
                  label={({ name, percent }) => `${name}: ${(percent * 100).toFixed(0)}%`}
                >
                  {pieData.map((entry, index) => (
                    <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                  ))}
                </Pie>
                <Legend />
              </PieChart>
            </ResponsiveContainer>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
