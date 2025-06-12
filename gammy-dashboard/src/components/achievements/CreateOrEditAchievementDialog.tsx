import { useCreateAchievementMutation, useUpdateAchievementMutation } from "@/lib/api/mutations";
import type { components } from "@/lib/api/v1";
import { Dialog, DialogContent, DialogTitle, DialogTrigger } from "../ui/dialog";
import { Button } from "../ui/button";
import { useEffect, useState } from "react";
import { Input } from "../ui/input";

export default function CreateOrEditAchievementDialog({
  achievement
}: {
  achievement?: components["schemas"]["GameAchievementEntity"]
}) {
  const create = useCreateAchievementMutation();
  const update = useUpdateAchievementMutation();

  const [open, setOpen] = useState(false);

  const [ach, setAch] = useState<components["schemas"]["GameAchievementEntity"]>({
    apiName: "",
    name: "",
    description: "",
    unlockExpression: ""
  });

  useEffect(() => {
    if (achievement) setAch(achievement)
  }, [achievement]);

  return <>
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button size={"sm"}>
          {achievement ? "Edit" : "New Achievement"}
        </Button>
      </DialogTrigger>
      <DialogContent>
        <DialogTitle>
          {achievement ? "Edit Achievement" : "New Achievement"}
        </DialogTitle>

        <Input placeholder="API Name" value={ach.apiName} onChange={(e) => setAch(a => ({ ...a, apiName: e.target.value }))} />
        <Input placeholder="Display Name" value={ach.name} onChange={(e) => setAch(a => ({ ...a, name: e.target.value }))} />
        <Input placeholder="Description" value={ach.description} onChange={(e) => setAch(a => ({ ...a, description: e.target.value }))} />
        <Input placeholder="Unlock Expression" value={ach.unlockExpression ?? ""} onChange={(e) => setAch(a => ({ ...a, unlockExpression: e.target.value }))} />

        <Button onClick={() => {
          if (achievement) {
            update.mutateAsync({ body: ach }).then(a => setOpen(false))
          } else {
            create.mutateAsync({ body: ach }).then(a => setOpen(false))
          }
        }}>{achievement ? "Save" : "Create"}</Button>
      </DialogContent>
    </Dialog>
  </>
}