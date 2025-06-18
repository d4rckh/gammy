import {Button} from "@/components/ui/button";
import {useDeleteGameStat} from "@/lib/api/mutations";
import type {components} from "@/lib/api/v1";

export default function DeleteStatsButton({stat}: { stat: components["schemas"]["GameStatEntity"] }) {
    const deleteOverride = useDeleteGameStat();

    return <Button
        onClick={() => {
            deleteOverride.mutate({
                body: stat
            })
        }}
        size={"sm"} variant={"destructive"}>
        Delete
    </Button>
}