import React from "react";
import {useAnalyticsAnalytics} from "@/lib/api/queries";
import {Bar, BarChart, CartesianGrid, ResponsiveContainer, Tooltip, XAxis, YAxis,} from "recharts";
import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card";

interface AchievementAnalyticsProps {
    apiName: string;
}

export default function AchievementAnalytics({apiName}: AchievementAnalyticsProps) {
    const {data, isLoading, isError} = useAnalyticsAnalytics(apiName);

    if (isLoading) return <div>Loading achievement analytics...</div>;
    if (isError || !data) return <div>Failed to load analytics.</div>;

    const {achievementEntity, lastDays, perDayUnlocks} = data;

    const barData = Object.entries(perDayUnlocks).map(([date, count]) => ({
        date,
        count,
    }));

    return (
        <div className="space-y-6">
            <div className="text-xl font-semibold">
                Achievement Analytics for <span
                className="text-primary">{achievementEntity.name}</span> (Last {lastDays} days)
            </div>

            <Card>
                <CardHeader>
                    <CardTitle>Unlocks Per Day</CardTitle>
                </CardHeader>
                <CardContent className="h-72">
                    <ResponsiveContainer width="100%" height="100%">
                        <BarChart data={barData} margin={{top: 20, right: 30, left: 0, bottom: 5}}>
                            <CartesianGrid strokeDasharray="3 3"/>
                            <XAxis dataKey="date" tick={{fontSize: 12}}/>
                            <YAxis allowDecimals={false}/>
                            <Bar dataKey="count" fill="#4f46e5"/>
                            <Tooltip/>
                        </BarChart>
                    </ResponsiveContainer>
                </CardContent>
            </Card>
        </div>
    );
}
