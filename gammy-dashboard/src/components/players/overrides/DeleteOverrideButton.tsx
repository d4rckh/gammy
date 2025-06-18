import {Button} from "@/components/ui/button";
import {useDeletePlayerParameterOverrideMutation} from "@/lib/api/mutations";
import type {components} from "@/lib/api/v1";

export default function DeleteOverrideButton({override}: {
    override: components["schemas"]["PlayerParameterOverrideEntity"]
}) {
    const deleteOverride = useDeletePlayerParameterOverrideMutation(override.player?.id ?? 0);

    return <Button
        onClick={() => {
            deleteOverride.mutate({
                body: override
            })
        }}
        size={"sm"} variant={"destructive"}>
        Delete
    </Button>
}