import React from "react";
import {useStatAnalytics} from "@/lib/api/queries";
import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card";
import {Bar, BarChart, CartesianGrid, Cell, Legend, Pie, PieChart, ResponsiveContainer, XAxis, YAxis,} from "recharts";
import {ChartContainer, ChartTooltip, ChartTooltipContent} from "../ui/chart";
import AnalyticsCard from "../ui/custom/AnalyticsCard";

interface StatsAnalyticsProps {
    apiName: string;
}

export default function StatsAnalytics({apiName}: StatsAnalyticsProps) {
    const {data: analytics, isLoading, isError} = useStatAnalytics(apiName);

    if (isLoading) return <div>Loading statistics...</div>;
    if (isError || !analytics) return <div>Failed to load statistics.</div>;

    const {
        totalNonDefaultEntries,
        averageValue,
        standardDeviation,
        distributionOfValues,
        mostCommonValue,
        percentAboveAverage,
    } = analytics;

    const barData = Object.entries(distributionOfValues).map(([value, count]) => ({
        name: value,
        count,
    }));

    const pieData = [
        {name: "Above", value: percentAboveAverage},
        {name: "Below or Equal", value: 100 - percentAboveAverage},
    ];

    const COLORS = ["#4f46e5", "#e0e7ff"];

    return (
        <div className="space-y-6">
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-6">
                <AnalyticsCard name="Total Non-Default Entries" value={totalNonDefaultEntries}/>
                <AnalyticsCard name="Average Value" value={+averageValue.toFixed(2)}/>
                <AnalyticsCard name="Standard Deviation" value={+standardDeviation.toFixed(2)}/>
                <AnalyticsCard name="Most Common Value" value={mostCommonValue ? +mostCommonValue.toFixed(1) : 0}/>
                <AnalyticsCard name="Percent Above Average" value={+percentAboveAverage.toFixed(1)}/>
            </div>

            <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                <Card>
                    <CardHeader>
                        <CardTitle>Value Distribution</CardTitle>
                    </CardHeader>
                    <CardContent className="h-64">
                        <ChartContainer config={{}}>
                            <ResponsiveContainer width="100%" height="100%">
                                <BarChart data={barData} margin={{top: 20, right: 30, left: 0, bottom: 5}}>
                                    <CartesianGrid strokeDasharray="3 3"/>
                                    <XAxis dataKey="name"/>
                                    <YAxis/>
                                    <ChartTooltip content={<ChartTooltipContent/>}/>
                                    <Bar dataKey="count" fill="#4f46e5"/>
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
                                    label={({name, percent}) => `${name}: ${(percent * 100).toFixed(0)}%`}
                                >
                                    {pieData.map((_, index) => (
                                        <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]}/>
                                    ))}
                                </Pie>
                                <Legend/>
                            </PieChart>
                        </ResponsiveContainer>
                    </CardContent>
                </Card>
            </div>
        </div>
    );
}
