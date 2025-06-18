import {Card, CardContent, CardHeader, CardTitle} from "../card";

export default function AnalyticsCard({name, value}: { name: string, value: number }) {
    return (
        <Card className="hover:shadow-lg transition-shadow duration-300 rounded-lg">
            <CardHeader>
                <CardTitle className="flex items-center gap-2 text-lg font-semibold text-primary-600">
                    {name}
                </CardTitle>
            </CardHeader>
            <CardContent className="text-5xl">
                {value}
            </CardContent>
        </Card>
    );
}