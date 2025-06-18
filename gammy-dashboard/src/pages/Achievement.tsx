import AchievementAnalytics from "@/components/achievements/AchievementAnalytics";
import PageTitle from "@/components/pageTitle";
import {useParams} from "react-router";

export default function Achievement() {
    const {apiName} = useParams();

    return <>
        <PageTitle
            title={"Achievement - " + apiName}
            description="Here you can see insights about this achievement"
        />

        <AchievementAnalytics apiName={apiName ?? ""}/>
    </>
}