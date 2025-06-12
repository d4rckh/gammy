import { useCreateAchievementMutation, useUpdateAchievementMutation } from "@/lib/api/mutations";
import type { components } from "@/lib/api/v1";
import { Dialog, DialogContent, DialogTitle, DialogTrigger } from "../ui/dialog";
import { Button } from "../ui/button";
import { useEffect, useState } from "react";
import { Input } from "../ui/input";
import { Label } from "../ui/label";

type GameAchievementEntity = components["schemas"]["GameAchievementEntity"];

export default function CreateOrEditAchievementDialog({
  achievement,
}: {
  achievement?: GameAchievementEntity;
}) {
  const create = useCreateAchievementMutation();
  const update = useUpdateAchievementMutation();

  const [open, setOpen] = useState(false);
  const [body, setBody] = useState<GameAchievementEntity>({
    apiName: "",
    name: "",
    description: "",
    unlockExpression: "",
  });

  const [errors, setErrors] = useState<{ [key: string]: boolean }>({});

  useEffect(() => {
    if (achievement) setBody(achievement);
  }, [achievement]);

  const handleChange = (field: keyof GameAchievementEntity, value: string) => {
    setBody((prev) => ({ ...prev, [field]: value }));
    setErrors((prev) => ({ ...prev, [field]: false }));
  };

  const validateAndSubmit = () => {
    const newErrors: typeof errors = {};
    if (!body.apiName?.trim()) newErrors.apiName = true;
    if (!body.name?.trim()) newErrors.name = true;
    if (!body.description?.trim()) newErrors.description = true;
    if (!body.unlockExpression?.trim()) newErrors.unlockExpression = true;

    setErrors(newErrors);
    if (Object.keys(newErrors).length > 0) return;

    const mutation = achievement ? update : create;
    mutation.mutateAsync({ body }).then(() => setOpen(false));
  };

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button size="sm">{achievement ? "Edit" : "New Achievement"}</Button>
      </DialogTrigger>

      <DialogContent>
        <DialogTitle>{achievement ? "Edit Achievement" : "New Achievement"}</DialogTitle>

        <Label>API Name</Label>
        <Input
          placeholder="API Name"
          value={body.apiName ?? ""}
          onChange={(e) => handleChange("apiName", e.target.value)}
          className={errors.apiName ? "border-red-500" : ""}
        />

        <Label>Display Name</Label>
        <Input
          placeholder="Display Name"
          value={body.name ?? ""}
          onChange={(e) => handleChange("name", e.target.value)}
          className={errors.name ? "border-red-500" : ""}
        />

        <Label>Description</Label>
        <Input
          placeholder="Description"
          value={body.description ?? ""}
          onChange={(e) => handleChange("description", e.target.value)}
          className={errors.description ? "border-red-500" : ""}
        />

        <Label>Unlock Expression</Label>
        <Input
          placeholder="Unlock Expression"
          value={body.unlockExpression ?? ""}
          onChange={(e) => handleChange("unlockExpression", e.target.value)}
          className={errors.unlockExpression ? "border-red-500" : ""}
        />

        <Button onClick={validateAndSubmit}>
          {achievement ? "Save" : "Create"}
        </Button>
      </DialogContent>
    </Dialog>
  );
}
