import {Button} from "@/components/ui/button";
import {useDeleteAchievementMutation} from "@/lib/api/mutations";
import type {components} from "@/lib/api/v1";

export default function DeleteAchievementButton({achievement}: {
    achievement: components["schemas"]["GameAchievementEntity"]
}) {
    const deleteOverride = useDeleteAchievementMutation();

    return <Button
        onClick={() => {
            deleteOverride.mutate({
                body: achievement
            })
        }}
        size={"sm"} variant={"destructive"}>
        Delete
    </Button>
}